/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.ui.hopgui.assistant;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.MessageBox;
import org.apache.hop.ui.core.gui.GuiResource;
import org.apache.hop.ui.util.EnvironmentUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Customer-service style chat panel for the personal assistant.
 *
 * <p>Design: "完全重建" pattern. All state is static (persists across dialog open/close cycles). {@link
 * #refreshChatDisplay()} rebuilds the entire chat text from {@link #sharedHistory} + {@link
 * #currentResponse} + {@link #currentReasoning} on every update.
 *
 * <p>RAP compatibility: Only {@code setSelection(int)} is safe on RAP Text widgets.
 */
public class LlmAssistantDialog extends Dialog {

  private static final Class<?> PKG = LlmAssistantDialog.class;

  private static final int MAX_HISTORY_TURNS = 20;
  private static final int PANEL_WIDTH = 440;
  private static final int PANEL_HEIGHT = 580;
  private static final int HEADER_HEIGHT = 38;

  private static final LogChannel log = new LogChannel("LlmAssistantDialog");

  // ── Static state: persists across dialog instances ──

  private static final List<LlmClient.ChatMessage> sharedHistory = new CopyOnWriteArrayList<>();
  private static final StringBuffer currentResponse = new StringBuffer();
  private static final StringBuffer currentReasoning = new StringBuffer();

  private static final AtomicBoolean cancelled = new AtomicBoolean(false);
  private static final AtomicBoolean isSending = new AtomicBoolean(false);

  // ── Instance state ──

  private final LlmClient client;

  private Shell shell;
  private Text chatArea;
  private Text inputArea;
  private Button sendButton;
  private boolean busy;
  private Object pushSession;

  private Runnable onClose;

  public LlmAssistantDialog(Shell parent) {
    super(parent, SWT.NONE);
    this.client = new LlmClient(LlmAssistantConfig.getInstance());
  }

  public void setOnClose(Runnable onClose) {
    this.onClose = onClose;
  }

  public void open() {
    Shell parent = getParent();
    Display display = parent.getDisplay();

    shell = new Shell(parent, SWT.NO_TRIM | SWT.MODELESS);
    shell.setText(BaseMessages.getString(PKG, "LlmAssistant.Dialog.Title"));
    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = 0;
    formLayout.marginHeight = 0;
    shell.setLayout(formLayout);
    PropsUi.setLook(shell);

    createContent();
    refreshChatDisplay();

    if (isSending.get()) {
      busy = true;
      if (inputArea != null && !inputArea.isDisposed()) {
        inputArea.setEnabled(false);
      }
    }

    shell.setSize(PANEL_WIDTH, PANEL_HEIGHT);
    positionBottomRight(parent);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    if (onClose != null) {
      onClose.run();
    }
  }

  private void positionBottomRight(Shell parent) {
    Rectangle clientArea;
    Monitor monitor = parent.getMonitor();
    if (monitor != null) {
      clientArea = monitor.getClientArea();
    } else {
      clientArea = parent.getDisplay().getBounds();
    }
    int x = clientArea.x + clientArea.width - PANEL_WIDTH - PropsUi.getMargin();
    int y = clientArea.y + clientArea.height - PANEL_HEIGHT - PropsUi.getMargin();
    shell.setLocation(x, y);
  }

  private void createContent() {
    // ---- Custom title bar ----
    Composite header = new Composite(shell, SWT.NONE);
    FormData fdHeader = new FormData();
    fdHeader.left = new FormAttachment(0, 0);
    fdHeader.right = new FormAttachment(100, 0);
    fdHeader.top = new FormAttachment(0, 0);
    fdHeader.height = HEADER_HEIGHT;
    header.setLayoutData(fdHeader);
    FormLayout headerLayout = new FormLayout();
    headerLayout.marginWidth = PropsUi.getMargin();
    headerLayout.marginHeight = PropsUi.getMargin() / 2;
    header.setLayout(headerLayout);
    header.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));

    Label titleLabel = new Label(header, SWT.NONE);
    titleLabel.setText(BaseMessages.getString(PKG, "LlmAssistant.Dialog.Title"));
    titleLabel.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
    titleLabel.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
    titleLabel.setFont(GuiResource.getInstance().getFontDefault());
    FormData fdTitle = new FormData();
    fdTitle.left = new FormAttachment(0, 0);
    fdTitle.top = new FormAttachment(0, 0);
    fdTitle.bottom = new FormAttachment(100, 0);
    titleLabel.setLayoutData(fdTitle);

    Button closeButton = new Button(header, SWT.PUSH);
    closeButton.setText("\u00D7");
    closeButton.setToolTipText(BaseMessages.getString(PKG, "LlmAssistant.Button.Close"));
    FormData fdClose = new FormData();
    fdClose.right = new FormAttachment(100, 0);
    fdClose.top = new FormAttachment(0, 0);
    fdClose.width = 30;
    fdClose.height = 24;
    closeButton.setLayoutData(fdClose);
    closeButton.addListener(SWT.Selection, e -> closePanel());

    Button minimizeButton = new Button(header, SWT.PUSH);
    minimizeButton.setText("\u2013");
    minimizeButton.setToolTipText(BaseMessages.getString(PKG, "LlmAssistant.Button.Minimize"));
    FormData fdMin = new FormData();
    fdMin.right = new FormAttachment(closeButton, -PropsUi.getMargin());
    fdMin.top = new FormAttachment(0, 0);
    fdMin.width = 30;
    fdMin.height = 24;
    minimizeButton.setLayoutData(fdMin);
    minimizeButton.addListener(SWT.Selection, e -> closePanel());

    Button clearHistoryButton = new Button(header, SWT.PUSH);
    clearHistoryButton.setText("\uD83D\uDDD1");
    clearHistoryButton.setToolTipText(
        BaseMessages.getString(PKG, "LlmAssistant.Button.ClearHistory"));
    FormData fdClear = new FormData();
    fdClear.right = new FormAttachment(minimizeButton, -PropsUi.getMargin());
    fdClear.top = new FormAttachment(0, 0);
    fdClear.width = 30;
    fdClear.height = 24;
    clearHistoryButton.setLayoutData(fdClear);
    clearHistoryButton.addListener(SWT.Selection, e -> clearHistory());

    // ---- Chat history ----
    chatArea = new Text(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP | SWT.READ_ONLY);
    FormData fdChat = new FormData();
    fdChat.left = new FormAttachment(0, PropsUi.getMargin());
    fdChat.right = new FormAttachment(100, -PropsUi.getMargin());
    fdChat.top = new FormAttachment(header, PropsUi.getMargin());
    fdChat.bottom = new FormAttachment(62, 0);
    chatArea.setLayoutData(fdChat);
    PropsUi.setLook(chatArea);
    chatArea.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

    // ---- Input area ----
    inputArea = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
    FormData fdInput = new FormData();
    fdInput.left = new FormAttachment(0, PropsUi.getMargin());
    fdInput.right = new FormAttachment(100, -PropsUi.getMargin());
    fdInput.top = new FormAttachment(chatArea, PropsUi.getMargin());
    fdInput.bottom = new FormAttachment(88, 0);
    inputArea.setLayoutData(fdInput);
    inputArea.setMessage(BaseMessages.getString(PKG, "LlmAssistant.Input.Placeholder"));
    PropsUi.setLook(inputArea);
    inputArea.setFocus();
    inputArea.addListener(
        SWT.Traverse,
        event -> {
          if (event.detail == SWT.TRAVERSE_RETURN && (event.stateMask & SWT.SHIFT) == 0 && !busy) {
            event.doit = false;
            sendMessage();
          }
        });

    // ---- Send button ----
    sendButton = new Button(shell, SWT.PUSH);
    sendButton.setText(
        BaseMessages.getString(
            PKG, isSending.get() ? "LlmAssistant.Button.Stop" : "LlmAssistant.Button.Send"));
    FormData fdSend = new FormData();
    fdSend.right = new FormAttachment(100, -PropsUi.getMargin());
    fdSend.top = new FormAttachment(inputArea, PropsUi.getMargin());
    fdSend.bottom = new FormAttachment(100, -PropsUi.getMargin());
    fdSend.width = 100;
    sendButton.setLayoutData(fdSend);
    PropsUi.setLook(sendButton);
    sendButton.setFont(GuiResource.getInstance().getFontDefault());
    sendButton.addListener(
        SWT.Selection,
        e -> {
          if (busy) {
            cancelMessage();
          } else {
            sendMessage();
          }
        });
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // refreshChatDisplay — rebuild entire chatArea from static state
  // ──────────────────────────────────────────────────────────────────────────────

  private void refreshChatDisplay() {
    if (chatArea == null || chatArea.isDisposed()) return;

    String youLabel = BaseMessages.getString(PKG, "LlmAssistant.Message.You");
    String assistantLabel = BaseMessages.getString(PKG, "LlmAssistant.Message.Assistant");

    StringBuilder sb = new StringBuilder();

    // Welcome 始终固定在聊天区顶部
    sb.append(assistantLabel)
        .append(":\n")
        .append(BaseMessages.getString(PKG, "LlmAssistant.Dialog.Welcome"))
        .append("\n");

    for (LlmClient.ChatMessage msg : sharedHistory) {
      sb.append("\n");
      String speaker = "user".equals(msg.role()) ? youLabel : assistantLabel;
      sb.append(speaker).append(":\n").append(msg.content()).append("\n");
    }

    // Show in-progress response
    if (isSending.get()) {
      sb.append("\n");
      sb.append(assistantLabel).append(":\n");

      String reasoning = currentReasoning.toString();
      String partial = currentResponse.toString();

      if (!partial.isEmpty()) {
        // Has actual content
        if (!reasoning.isEmpty()) {
          sb.append("💭 思考:\n").append(reasoning).append("\n\n");
        }
        sb.append(partial);
      } else if (!reasoning.isEmpty()) {
        // Only reasoning so far
        sb.append("💭 思考:\n").append(reasoning).append("\n");
      } else {
        sb.append(BaseMessages.getString(PKG, "LlmAssistant.Message.Thinking"));
      }
      sb.append("\n");
    }

    chatArea.setText(sb.toString());
    scrollToBottom();
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Send / Cancel
  // ──────────────────────────────────────────────────────────────────────────────

  private void sendMessage() {
    String question = inputArea.getText().trim();
    // 立即清空输入框，避免 RAP 环境下重复点击读到缓存旧值
    inputArea.setText("");
    if (question.isEmpty()) {
      // 空输入：在发送按钮上方冒泡提示，不触碰 sharedHistory，welcome 保留
      showBubbleTip(BaseMessages.getString(PKG, "LlmAssistant.Message.EmptyQuestion"));
      inputArea.setFocus();
      return;
    }
    if (!LlmAssistantConfig.getInstance().isAvailable()) {
      // Show hint as a bubble above the send button — do NOT pollute sharedHistory so the
      // welcome message is preserved.
      showBubbleTip(BaseMessages.getString(PKG, "LlmAssistant.Message.NotConfigured"));
      inputArea.setFocus();
      return;
    }

    sharedHistory.add(new LlmClient.ChatMessage("user", question));
    trimHistory();
    currentResponse.setLength(0);
    currentReasoning.setLength(0);
    cancelled.set(false);
    isSending.set(true);

    setBusy(true);
    startPushSession();
    refreshChatDisplay();

    final Display display = shell.getDisplay();
    List<LlmClient.ChatMessage> snapshot = List.copyOf(sharedHistory);

    Thread worker =
        new Thread(
            () -> {
              log.logBasic("Worker thread started");
              try {
                client.streamChat(
                    snapshot,
                    cancelled,
                    new LlmClient.StreamCallback() {
                      @Override
                      public void onReasoning(String chunk) {
                        currentReasoning.append(chunk);
                        display.asyncExec(
                            () -> {
                              log.logBasic(
                                  "asyncExec onReasoning, disposed="
                                      + (chatArea == null || chatArea.isDisposed()));
                              if (chatArea != null && !chatArea.isDisposed()) {
                                refreshChatDisplay();
                              }
                            });
                      }

                      @Override
                      public void onChunk(String chunk) {
                        currentResponse.append(chunk);
                        display.asyncExec(
                            () -> {
                              log.logBasic(
                                  "asyncExec onChunk, disposed="
                                      + (chatArea == null || chatArea.isDisposed()));
                              if (chatArea != null && !chatArea.isDisposed()) {
                                refreshChatDisplay();
                              }
                            });
                      }

                      @Override
                      public void onComplete(String fullText) {
                        log.logBasic("onComplete called, fullText length=" + fullText.length());
                        sharedHistory.add(new LlmClient.ChatMessage("assistant", fullText));
                        trimHistory();
                        currentResponse.setLength(0);
                        currentReasoning.setLength(0);
                        isSending.set(false);
                        display.asyncExec(
                            () -> {
                              log.logBasic(
                                  "asyncExec onComplete, disposed="
                                      + (chatArea == null || chatArea.isDisposed()));
                              if (chatArea == null || chatArea.isDisposed()) {
                                stopPushSession();
                                return;
                              }
                              refreshChatDisplay();
                              setBusy(false);
                              stopPushSession();
                              if (inputArea != null && !inputArea.isDisposed()) {
                                inputArea.setText("");
                                inputArea.setFocus();
                              }
                            });
                      }

                      @Override
                      public void onError(Exception e) {
                        boolean wasCancelled = cancelled.get();
                        log.logBasic(
                            "onError called: " + e.getMessage() + ", wasCancelled=" + wasCancelled);
                        isSending.set(false);
                        display.asyncExec(
                            () -> {
                              if (chatArea == null || chatArea.isDisposed()) {
                                stopPushSession();
                                return;
                              }
                              // If cancelled, cancelMessage() already handled everything
                              if (wasCancelled) {
                                stopPushSession();
                                return;
                              }
                              // Real error: save partial response to history
                              String partial = currentResponse.toString();
                              if (!partial.isEmpty()) {
                                sharedHistory.add(new LlmClient.ChatMessage("assistant", partial));
                                trimHistory();
                              }
                              sharedHistory.add(
                                  new LlmClient.ChatMessage(
                                      "assistant",
                                      BaseMessages.getString(
                                          PKG, "LlmAssistant.Message.Error", e.getMessage())));
                              currentResponse.setLength(0);
                              currentReasoning.setLength(0);
                              refreshChatDisplay();
                              setBusy(false);
                              stopPushSession();
                              if (inputArea != null && !inputArea.isDisposed()) {
                                inputArea.setText("");
                                inputArea.setFocus();
                              }
                            });
                      }
                    });
              } catch (Exception e) {
                log.logBasic("Worker exception: " + e.getMessage(), e);
                boolean wasCancelled = cancelled.get();
                isSending.set(false);
                display.asyncExec(
                    () -> {
                      if (chatArea == null || chatArea.isDisposed()) {
                        stopPushSession();
                        return;
                      }
                      if (wasCancelled) {
                        stopPushSession();
                        return;
                      }
                      String partial = currentResponse.toString();
                      if (!partial.isEmpty()) {
                        sharedHistory.add(new LlmClient.ChatMessage("assistant", partial));
                        trimHistory();
                      }
                      currentResponse.setLength(0);
                      currentReasoning.setLength(0);
                      sharedHistory.add(
                          new LlmClient.ChatMessage(
                              "assistant",
                              BaseMessages.getString(
                                  PKG, "LlmAssistant.Message.Error", e.getMessage())));
                      refreshChatDisplay();
                      setBusy(false);
                      stopPushSession();
                      if (inputArea != null && !inputArea.isDisposed()) {
                        inputArea.setText("");
                        inputArea.setFocus();
                      }
                    });
              }
              log.logBasic("Worker thread finished");
            },
            "Hop-LlmAssistant");
    worker.setDaemon(true);
    worker.start();
  }

  private void cancelMessage() {
    if (!busy) return;

    cancelled.set(true);
    isSending.set(false);

    String partial = currentResponse.toString();
    if (!partial.isEmpty()) {
      sharedHistory.add(new LlmClient.ChatMessage("assistant", partial));
      trimHistory();
    }
    currentResponse.setLength(0);
    currentReasoning.setLength(0);

    // Clear input to prevent stale text from being re-sent
    if (inputArea != null && !inputArea.isDisposed()) {
      inputArea.setText("");
    }
    setBusy(false);
    stopPushSession();
    refreshChatDisplay();
    if (inputArea != null && !inputArea.isDisposed()) {
      inputArea.setFocus();
    }
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Panel operations
  // ──────────────────────────────────────────────────────────────────────────────

  private void closePanel() {
    if (shell != null && !shell.isDisposed()) {
      shell.dispose();
    }
  }

  private void clearHistory() {
    if (sharedHistory.isEmpty() && currentResponse.length() == 0) return;
    MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
    messageBox.setText(BaseMessages.getString(PKG, "LlmAssistant.Message.ClearHistory.Title"));
    messageBox.setMessage(BaseMessages.getString(PKG, "LlmAssistant.Message.ClearHistory.Message"));
    if (messageBox.open() == SWT.YES) {
      sharedHistory.clear();
      currentResponse.setLength(0);
      currentReasoning.setLength(0);
      refreshChatDisplay();
    }
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // RAP ClientPushSession — with detailed logging for debugging
  // ──────────────────────────────────────────────────────────────────────────────

  private void startPushSession() {
    if (!EnvironmentUtils.getInstance().isWeb()) {
      log.logBasic("startPushSession: not web environment, skipping");
      return;
    }
    try {
      // RAP 4.4 uses org.eclipse.rap.rwt.service.ServerPushSession
      Class<?> pushSessionClass = Class.forName("org.eclipse.rap.rwt.service.ServerPushSession");
      pushSession = pushSessionClass.getDeclaredConstructor().newInstance();
      Method startMethod = pushSessionClass.getMethod("start");
      startMethod.invoke(pushSession);
      log.logBasic("startPushSession: SUCCESS");
    } catch (Exception e) {
      log.logBasic("startPushSession FAILED: " + e.getClass().getName() + ": " + e.getMessage());
    }
  }

  private void stopPushSession() {
    if (pushSession == null) return;
    try {
      Class<?> pushSessionClass = Class.forName("org.eclipse.rap.rwt.service.ServerPushSession");
      Method stopMethod = pushSessionClass.getMethod("stop");
      stopMethod.invoke(pushSession);
      log.logBasic("stopPushSession: SUCCESS");
    } catch (Exception e) {
      log.logBasic("stopPushSession FAILED: " + e.getMessage());
    }
    pushSession = null;
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Utilities
  // ──────────────────────────────────────────────────────────────────────────────

  private void scrollToBottom() {
    chatArea.setSelection(chatArea.getCharCount());
    if (!EnvironmentUtils.getInstance().isWeb()) {
      chatArea.showSelection();
    }
  }

  private void trimHistory() {
    while (sharedHistory.size() > MAX_HISTORY_TURNS * 2) {
      sharedHistory.remove(0);
    }
  }

  private void setBusy(boolean value) {
    busy = value;
    isSending.set(value);
    if (sendButton != null && !sendButton.isDisposed()) {
      sendButton.setEnabled(true);
      sendButton.setText(
          BaseMessages.getString(
              PKG, value ? "LlmAssistant.Button.Stop" : "LlmAssistant.Button.Send"));
    }
    if (inputArea != null && !inputArea.isDisposed()) {
      inputArea.setEnabled(!value);
    }
  }

  /** Show a brief tooltip-style bubble above the send button. */
  private void showBubbleTip(String message) {
    if (sendButton == null || sendButton.isDisposed()) return;
    Display display = sendButton.getDisplay();

    Shell tipShell = new Shell(shell, SWT.NO_TRIM | SWT.MODELESS | SWT.TOOL);
    org.eclipse.swt.layout.FillLayout fillLayout = new org.eclipse.swt.layout.FillLayout();
    fillLayout.marginWidth = 6;
    fillLayout.marginHeight = 4;
    tipShell.setLayout(fillLayout);
    Label tipLabel = new Label(tipShell, SWT.NONE);
    tipLabel.setText(message);
    tipLabel.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
    tipLabel.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
    PropsUi.setLook(tipLabel);

    // Position: centered above the send button
    tipShell.pack();
    org.eclipse.swt.graphics.Point tipSize = tipShell.getSize();
    org.eclipse.swt.graphics.Point btnAbs =
        sendButton.getParent().toDisplay(sendButton.getLocation());
    int x = btnAbs.x + sendButton.getSize().x / 2 - tipSize.x / 2;
    int y = btnAbs.y - tipSize.y - 6;
    tipShell.setLocation(x, y);
    tipShell.setVisible(true);

    // Auto-dismiss after 2 seconds
    display.timerExec(
        2000,
        () -> {
          if (!tipShell.isDisposed()) {
            tipShell.dispose();
          }
        });
  }
}
