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
import java.util.stream.Collectors;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.MessageBox;
import org.apache.hop.ui.core.gui.GuiResource;
import org.apache.hop.ui.hopgui.assistant.knowledgebase.KnowledgeBaseService;
import org.apache.hop.ui.hopgui.assistant.skills.Skill;
import org.apache.hop.ui.hopgui.assistant.skills.SkillManager;
import org.apache.hop.ui.util.EnvironmentUtils;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
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
  private Browser chatArea;
  private Text inputArea;
  private Button sendButton;
  private Label activeSkillsLabel;
  private boolean busy;
  private Object pushSession;

  // Throttle: avoid full HTML re-render on every streaming chunk
  private volatile boolean refreshScheduled = false;

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

    // In RAP (web) environment, minimize button doesn't trigger SWT.Iconify event reliably.
    // Remove SWT.MIN flag in web mode so users use close button (which works correctly).
    int shellStyle = SWT.DIALOG_TRIM | SWT.MODELESS | SWT.RESIZE | SWT.MAX;
    if (!EnvironmentUtils.getInstance().isWeb()) {
      shellStyle |= SWT.MIN;
    }
    shell = new Shell(parent, shellStyle);
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
      setInputAreaEnabled(false);
    }

    shell.setSize(PANEL_WIDTH, PANEL_HEIGHT);
    positionBottomRight(parent);

    // Handle minimize (desktop only): when minimized, trigger onClose callback to show floating
    // button
    shell.addListener(
        SWT.Iconify,
        e -> {
          if (onClose != null) {
            onClose.run();
          }
        });

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

  private void positionBottomRight(Composite parent) {
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
    // ---- Chat history ----
    Composite chatContainer = new Composite(shell, SWT.NONE);
    FormData fdChatContainer = new FormData();
    fdChatContainer.left = new FormAttachment(0, PropsUi.getMargin());
    fdChatContainer.right = new FormAttachment(100, -PropsUi.getMargin());
    fdChatContainer.top = new FormAttachment(0, PropsUi.getMargin());
    fdChatContainer.bottom = new FormAttachment(65, 0);
    chatContainer.setLayoutData(fdChatContainer);
    chatContainer.setLayout(new FormLayout());

    chatArea = new Browser(chatContainer, SWT.BORDER);
    FormData fdChatArea = new FormData();
    fdChatArea.left = new FormAttachment(0, 0);
    fdChatArea.right = new FormAttachment(100, 0);
    fdChatArea.top = new FormAttachment(0, 0);
    fdChatArea.bottom = new FormAttachment(100, 0);
    chatArea.setLayoutData(fdChatArea);

    // ---- Skill selector bar ----
    Composite skillBar = new Composite(shell, SWT.NONE);
    FormData fdSkillBar = new FormData();
    fdSkillBar.left = new FormAttachment(0, PropsUi.getMargin());
    fdSkillBar.right = new FormAttachment(100, -PropsUi.getMargin());
    fdSkillBar.top = new FormAttachment(chatContainer, PropsUi.getMargin());
    fdSkillBar.height = 36;
    skillBar.setLayoutData(fdSkillBar);
    FormLayout skillBarLayout = new FormLayout();
    skillBarLayout.marginWidth = 0;
    skillBarLayout.marginHeight = 4;
    skillBar.setLayout(skillBarLayout);
    PropsUi.setLook(skillBar);

    Label skillLabel = new Label(skillBar, SWT.NONE);
    skillLabel.setText("\ud83e\udde9");
    FormData fdSkillLabel = new FormData();
    fdSkillLabel.left = new FormAttachment(0, 0);
    fdSkillLabel.top = new FormAttachment(0, 0);
    fdSkillLabel.bottom = new FormAttachment(100, 0);
    skillLabel.setLayoutData(fdSkillLabel);
    PropsUi.setLook(skillLabel);

    Button addSkillButton = new Button(skillBar, SWT.PUSH);
    addSkillButton.setText(BaseMessages.getString(PKG, "LlmAssistant.Button.AddSkill"));
    addSkillButton.setToolTipText(
        BaseMessages.getString(PKG, "LlmAssistant.SkillBar.SelectManual"));
    FormData fdAddSkill = new FormData();
    fdAddSkill.right = new FormAttachment(100, 0);
    fdAddSkill.top = new FormAttachment(0, 0);
    fdAddSkill.bottom = new FormAttachment(100, 0);
    fdAddSkill.width = 80;
    addSkillButton.setLayoutData(fdAddSkill);
    PropsUi.setLook(addSkillButton);
    addSkillButton.addListener(
        SWT.Selection,
        e -> {
          showSkillPicker(activeSkillsLabel);
        });

    activeSkillsLabel = new Label(skillBar, SWT.NONE);
    FormData fdActiveLabel = new FormData();
    fdActiveLabel.left = new FormAttachment(skillLabel, 4);
    fdActiveLabel.right = new FormAttachment(addSkillButton, -PropsUi.getMargin());
    fdActiveLabel.top = new FormAttachment(0, 0);
    fdActiveLabel.bottom = new FormAttachment(100, 0);
    activeSkillsLabel.setLayoutData(fdActiveLabel);
    activeSkillsLabel.setText(buildActiveSkillsText());
    activeSkillsLabel.setToolTipText(
        BaseMessages.getString(PKG, "LlmAssistant.SkillBar.ActiveSkills"));
    PropsUi.setLook(activeSkillsLabel);

    // ---- Input area ----
    inputArea = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
    FormData fdInput = new FormData();
    fdInput.left = new FormAttachment(0, PropsUi.getMargin());
    fdInput.right = new FormAttachment(100, -PropsUi.getMargin());
    fdInput.top = new FormAttachment(skillBar, PropsUi.getMargin());
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
    fdSend.height = 28;
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
    doRefreshChatDisplay();
  }

  /**
   * Throttled refresh: schedules a single refresh after a short delay. Multiple calls during
   * streaming are coalesced into one render, eliminating flicker.
   */
  private void scheduleRefresh() {
    if (chatArea == null || isChatAreaDisposed()) return;
    if (refreshScheduled) return; // a refresh is already pending
    refreshScheduled = true;
    Display display = chatArea.getDisplay();
    display.timerExec(
        400,
        () -> {
          refreshScheduled = false;
          if (chatArea != null && !isChatAreaDisposed()) {
            doRefreshChatDisplay();
          }
        });
  }

  private void doRefreshChatDisplay() {
    if (chatArea == null || isChatAreaDisposed()) return;

    String youLabel = BaseMessages.getString(PKG, "LlmAssistant.Message.You");
    String assistantLabel = BaseMessages.getString(PKG, "LlmAssistant.Message.Assistant");

    StringBuilder mdBuilder = new StringBuilder();

    mdBuilder
        .append(assistantLabel)
        .append(":\n")
        .append(BaseMessages.getString(PKG, "LlmAssistant.Dialog.Welcome"))
        .append("\n\n");

    for (LlmClient.ChatMessage msg : sharedHistory) {
      String speaker = "user".equals(msg.role()) ? youLabel : assistantLabel;
      mdBuilder.append(speaker).append(":\n").append(msg.content()).append("\n\n");
    }

    if (isSending.get()) {
      mdBuilder.append(assistantLabel).append(":\n");

      String reasoning = currentReasoning.toString();
      String partial = currentResponse.toString();

      if (!partial.isEmpty()) {
        if (!reasoning.isEmpty()) {
          mdBuilder.append("💭 思考:\n").append(reasoning).append("\n\n");
        }
        mdBuilder.append(partial);
      } else if (!reasoning.isEmpty()) {
        mdBuilder.append("💭 思考:\n").append(reasoning).append("\n");
      } else {
        mdBuilder.append(BaseMessages.getString(PKG, "LlmAssistant.Message.Thinking"));
      }
    }

    Parser parser = Parser.builder().build();
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    String htmlContent = renderer.render(parser.parse(mdBuilder.toString()));

    boolean darkMode = PropsUi.getInstance().isDarkMode();
    String bodyColor = darkMode ? "#ffffff" : "#333";
    String backgroundColor = darkMode ? "#1a1a1a" : "#ffffff";
    String strongColor = darkMode ? "#ffffff" : "#1a1a1a";
    String codeBgColor = darkMode ? "#2d2d2d" : "#f4f4f4";
    String blockquoteColor = darkMode ? "#ccc" : "#666";
    String blockquoteBorderColor = darkMode ? "#555" : "#ddd";

    String html =
        "<!DOCTYPE html><html><head><style>"
            + "body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif; font-size: 13px; line-height: 1.5; margin: 8px; color: "
            + bodyColor
            + "; background-color: "
            + backgroundColor
            + "; }"
            + "p { margin: 4px 0; }"
            + "strong { font-weight: 600; color: "
            + strongColor
            + "; }"
            + "em { font-style: italic; }"
            + "code { background-color: "
            + codeBgColor
            + "; padding: 2px 4px; border-radius: 3px; font-family: 'Monaco', 'Menlo', monospace; font-size: 12px; }"
            + "pre { background-color: "
            + codeBgColor
            + "; padding: 8px; border-radius: 4px; overflow-x: auto; }"
            + "pre code { background: none; padding: 0; }"
            + "ul, ol { margin: 4px 0; padding-left: 20px; }"
            + "li { margin: 2px 0; }"
            + "blockquote { border-left: 3px solid "
            + blockquoteBorderColor
            + "; margin: 4px 0; padding-left: 8px; color: "
            + blockquoteColor
            + "; }"
            + "</style></head><body>"
            + htmlContent
            + "<script>window.scrollTo(0, document.body.scrollHeight);</script>"
            + "</body></html>";

    chatArea.setText(html);
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
      setInputAreaFocus();
      return;
    }
    if (!LlmAssistantConfig.getInstance().isAvailable()) {
      // Show hint as a bubble above the send button — do NOT pollute sharedHistory so the
      // welcome message is preserved.
      showBubbleTip(BaseMessages.getString(PKG, "LlmAssistant.Message.NotConfigured"));
      setInputAreaFocus();
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
                // RAG: retrieve relevant context from the knowledge base if enabled
                String ragContext = "";
                try {
                  KnowledgeBaseService kbService = KnowledgeBaseService.getInstance();
                  if (kbService.isEnabled()) {
                    ragContext = kbService.buildContextPrompt(question);
                    if (!ragContext.isEmpty()) {
                      log.logDetailed("RAG context injected: " + ragContext.length() + " chars");
                    }
                  }
                } catch (Exception ragEx) {
                  log.logError(
                      "RAG context retrieval failed, continuing without: " + ragEx.getMessage());
                }

                // Build combined extra context: active skills + RAG context
                List<Skill> activeSkills = SkillManager.getInstance().getActiveSkills();
                String skillContext = SkillManager.getInstance().buildSkillContext(activeSkills);
                StringBuilder extraContext = new StringBuilder();
                if (!skillContext.isEmpty()) {
                  extraContext.append(skillContext);
                  log.logDetailed("Skill context injected: " + skillContext.length() + " chars");
                }
                if (!ragContext.isEmpty()) {
                  extraContext.append(ragContext);
                }

                client.streamChat(
                    snapshot,
                    extraContext.length() > 0 ? extraContext.toString() : null,
                    cancelled,
                    new LlmClient.StreamCallback() {
                      @Override
                      public void onReasoning(String chunk) {
                        currentReasoning.append(chunk);
                        display.asyncExec(
                            () -> {
                              if (chatArea != null && !isChatAreaDisposed()) {
                                scheduleRefresh();
                              }
                            });
                      }

                      @Override
                      public void onChunk(String chunk) {
                        currentResponse.append(chunk);
                        display.asyncExec(
                            () -> {
                              if (chatArea != null && !isChatAreaDisposed()) {
                                scheduleRefresh();
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
                        // Increment usage count for all active skills on successful completion
                        SkillManager.getInstance().incrementUsageBatch(activeSkills);
                        display.asyncExec(
                            () -> {
                              if (chatArea == null || isChatAreaDisposed()) {
                                stopPushSession();
                                return;
                              }
                              refreshChatDisplay();
                              setBusy(false);
                              stopPushSession();
                              if (inputArea != null && !isInputAreaDisposed()) {
                                inputArea.setText("");
                                setInputAreaFocus();
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
                              if (chatArea == null || isChatAreaDisposed()) {
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
                              String errorMsg =
                                  e.getMessage() != null
                                      ? e.getMessage()
                                      : BaseMessages.getString(
                                          PKG, "LlmAssistant.Message.Error.Unknown");
                              sharedHistory.add(
                                  new LlmClient.ChatMessage(
                                      "assistant",
                                      BaseMessages.getString(
                                          PKG, "LlmAssistant.Message.Error", errorMsg)));
                              currentResponse.setLength(0);
                              currentReasoning.setLength(0);
                              refreshChatDisplay();
                              setBusy(false);
                              stopPushSession();
                              if (inputArea != null && !isInputAreaDisposed()) {
                                inputArea.setText("");
                                setInputAreaFocus();
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
                      if (chatArea == null || isChatAreaDisposed()) {
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
                      String errorMsg =
                          e.getMessage() != null
                              ? e.getMessage()
                              : BaseMessages.getString(PKG, "LlmAssistant.Message.Error.Unknown");
                      sharedHistory.add(
                          new LlmClient.ChatMessage(
                              "assistant",
                              BaseMessages.getString(PKG, "LlmAssistant.Message.Error", errorMsg)));
                      refreshChatDisplay();
                      setBusy(false);
                      stopPushSession();
                      if (inputArea != null && !isInputAreaDisposed()) {
                        inputArea.setText("");
                        setInputAreaFocus();
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
    if (inputArea != null && !isInputAreaDisposed()) {
      inputArea.setText("");
    }
    setBusy(false);
    stopPushSession();
    refreshChatDisplay();
    if (inputArea != null && !isInputAreaDisposed()) {
      setInputAreaFocus();
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

  private void toggleMaximize() {
    if (shell == null || shell.isDisposed()) return;
    Rectangle clientArea = shell.getParent().getClientArea();
    if (shell.getBounds().width == clientArea.width
        && shell.getBounds().height == clientArea.height) {
      shell.setSize(PANEL_WIDTH, PANEL_HEIGHT);
      positionBottomRight(shell.getParent());
    } else {
      shell.setSize(clientArea.width, clientArea.height);
      shell.setLocation(clientArea.x, clientArea.y);
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

  private void setInputAreaEnabled(boolean enabled) {
    if (inputArea != null && !inputArea.isDisposed()) {
      inputArea.setEnabled(enabled);
    }
  }

  private void setInputAreaFocus() {
    if (inputArea != null && !inputArea.isDisposed()) {
      inputArea.setFocus();
    }
  }

  private boolean isInputAreaDisposed() {
    return inputArea == null || inputArea.isDisposed();
  }

  private boolean isChatAreaDisposed() {
    return chatArea == null || chatArea.isDisposed();
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
    if (inputArea != null && !isInputAreaDisposed()) {
      setInputAreaEnabled(!value);
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

  // ──────────────────────────────────────────────────────────────────────────────
  // Skill selector helpers
  // ──────────────────────────────────────────────────────────────────────────────

  /**
   * Builds a summary text of currently active skills for the skill bar.
   *
   * @return comma-separated skill names with (Global) tag for global skills
   */
  private String buildActiveSkillsText() {
    List<Skill> active = SkillManager.getInstance().getActiveSkills();
    if (active.isEmpty()) {
      return "";
    }
    int maxDisplay = 3;
    String joined =
        active.stream()
            .limit(maxDisplay)
            .map(
                s ->
                    s.getName()
                        + (s.getTrigger() == Skill.TriggerType.GLOBAL
                            ? BaseMessages.getString(
                                org.apache.hop.ui.hopgui.assistant.skills.SkillManager.class,
                                "SkillsAssistant.GlobalTag")
                            : ""))
            .collect(Collectors.joining(", "));
    if (active.size() > maxDisplay) {
      joined += ", ...";
    }
    return joined;
  }

  /**
   * Shows a popup menu for selecting/deselecting manual skills.
   *
   * @param activeSkillsLabel the label to update after selection changes
   */
  private void showSkillPicker(Label activeSkillsLabel) {
    if (activeSkillsLabel == null || activeSkillsLabel.isDisposed()) return;

    // Dispose previous menu if any
    Menu oldMenu = activeSkillsLabel.getMenu();
    if (oldMenu != null && !oldMenu.isDisposed()) {
      oldMenu.dispose();
    }

    Menu menu = new Menu(activeSkillsLabel);
    // Only show enabled manual skills — disabled skills cannot be used in LLM Chat
    List<Skill> manualSkills =
        SkillManager.getInstance().getManualSkills().stream().filter(Skill::isEnabled).toList();
    if (manualSkills.isEmpty()) {
      MenuItem item = new MenuItem(menu, SWT.NONE);
      item.setText(BaseMessages.getString(PKG, "LlmAssistant.SkillBar.NoManualSkills"));
      item.setEnabled(false);
    } else {
      for (Skill skill : manualSkills) {
        MenuItem item = new MenuItem(menu, SWT.CHECK);
        String label = skill.getName();
        if (skill.getDescription() != null && !skill.getDescription().isBlank()) {
          String desc = skill.getDescription();
          if (desc.length() > 60) desc = desc.substring(0, 57) + "...";
          label += " — " + desc;
        }
        item.setText(label);
        item.setSelection(SkillManager.getInstance().isManualActive(skill.getName()));
        item.addListener(
            SWT.Selection,
            e -> {
              if (item.getSelection()) {
                SkillManager.getInstance().activateManual(skill.getName());
              } else {
                SkillManager.getInstance().deactivateManual(skill.getName());
              }
              if (!activeSkillsLabel.isDisposed()) {
                activeSkillsLabel.setText(buildActiveSkillsText());
              }
            });
      }
    }

    activeSkillsLabel.setMenu(menu);
    menu.setVisible(true);
  }
}
