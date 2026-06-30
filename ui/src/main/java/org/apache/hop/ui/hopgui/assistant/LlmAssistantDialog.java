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

import java.util.List;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.gui.GuiResource;
import org.apache.hop.ui.pipeline.transform.BaseTransformDialog;
import org.apache.hop.ui.util.SwtSvgImageUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Modal chat dialog backing the personal assistant. The conversation history is rendered in a
 * read-only text area; questions are sent to an OpenAI-compatible endpoint on a background thread
 * so the UI stays responsive. Closing or minimising the dialog returns the user to the floating
 * doctor-icon button.
 */
public class LlmAssistantDialog extends Dialog {

  private static final Class<?> PKG = LlmAssistantDialog.class;

  private static final int MAX_HISTORY_TURNS = 20;

  private final LlmClient client;
  private final List<LlmClient.ChatMessage> history = LlmClient.newHistory();

  private Shell shell;
  // A plain Text widget is used (rather than org.eclipse.swt.custom.StyledText) so the dialog also
  // loads and runs under Hop Web / RAP, where StyledText is unavailable.
  private Text chatArea;
  private Text inputArea;
  private Button sendButton;
  private boolean busy;

  public LlmAssistantDialog(Shell parent) {
    super(parent, SWT.NONE);
    this.client = new LlmClient(LlmAssistantConfig.getInstance());
  }

  /** Opens the dialog and blocks until it is closed or minimised. */
  public void open() {
    Shell parent = getParent();
    Display display = parent.getDisplay();

    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
    shell.setText(BaseMessages.getString(PKG, "LlmAssistant.Dialog.Title"));
    shell.setImage(
        SwtSvgImageUtil.getImageAsResource(display, "ui/images/assistant.svg")
            .getAsBitmapForSize(display, 32, 32));
    shell.setMinimumSize(380, 420);

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = PropsUi.getFormMargin();
    formLayout.marginHeight = PropsUi.getFormMargin();
    shell.setLayout(formLayout);
    PropsUi.setLook(shell);

    createContent();

    // Welcome message
    appendAssistantMessage(BaseMessages.getString(PKG, "LlmAssistant.Dialog.Welcome"));

    shell.addShellListener(
        new ShellAdapter() {
          @Override
          public void shellClosed(ShellEvent e) {
            // Just hide; the floating button stays around to re-open.
          }
        });

    BaseTransformDialog.setSize(shell, 520, 600);

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  private void createContent() {
    // Chat history
    chatArea = new Text(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP | SWT.READ_ONLY);
    FormData fdChat = new FormData();
    fdChat.left = new FormAttachment(0, 0);
    fdChat.right = new FormAttachment(100, 0);
    fdChat.top = new FormAttachment(0, 0);
    fdChat.bottom = new FormAttachment(60, 0);
    chatArea.setLayoutData(fdChat);
    PropsUi.setLook(chatArea);
    chatArea.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

    // Input area
    inputArea = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
    FormData fdInput = new FormData();
    fdInput.left = new FormAttachment(0, 0);
    fdInput.right = new FormAttachment(100, 0);
    fdInput.top = new FormAttachment(chatArea, PropsUi.getMargin());
    fdInput.bottom = new FormAttachment(85, 0);
    inputArea.setLayoutData(fdInput);
    inputArea.setMessage(BaseMessages.getString(PKG, "LlmAssistant.Input.Placeholder"));
    PropsUi.setLook(inputArea);
    inputArea.setFocus();
    inputArea.addListener(
        SWT.Traverse,
        event -> {
          // Enter sends the message (Shift+Enter inserts a newline).
          if (event.detail == SWT.TRAVERSE_RETURN && (event.stateMask & SWT.SHIFT) == 0 && !busy) {
            event.doit = false;
            sendMessage();
          }
        });

    // Buttons
    Composite buttonComposite = new Composite(shell, SWT.NONE);
    FormLayout buttonLayout = new FormLayout();
    buttonLayout.marginWidth = 0;
    buttonLayout.marginHeight = 0;
    buttonComposite.setLayout(buttonLayout);
    FormData fdButtons = new FormData();
    fdButtons.left = new FormAttachment(0, 0);
    fdButtons.right = new FormAttachment(100, 0);
    fdButtons.top = new FormAttachment(inputArea, PropsUi.getMargin());
    fdButtons.bottom = new FormAttachment(100, 0);
    buttonComposite.setLayoutData(fdButtons);
    PropsUi.setLook(buttonComposite);

    sendButton = new Button(buttonComposite, SWT.PUSH);
    sendButton.setText(BaseMessages.getString(PKG, "LlmAssistant.Button.Send"));
    FormData fdSend = new FormData();
    fdSend.right = new FormAttachment(100, 0);
    fdSend.top = new FormAttachment(0, 0);
    fdSend.width = 100;
    sendButton.setLayoutData(fdSend);
    PropsUi.setLook(sendButton);
    sendButton.setFont(GuiResource.getInstance().getFontDefault());
    sendButton.addListener(SWT.Selection, e -> sendMessage());

    Button minimizeButton = new Button(buttonComposite, SWT.PUSH);
    minimizeButton.setText(BaseMessages.getString(PKG, "LlmAssistant.Button.Minimize"));
    FormData fdMin = new FormData();
    fdMin.right = new FormAttachment(sendButton, -PropsUi.getMargin());
    fdMin.top = new FormAttachment(0, 0);
    fdMin.width = 100;
    minimizeButton.setLayoutData(fdMin);
    PropsUi.setLook(minimizeButton);
    minimizeButton.setFont(GuiResource.getInstance().getFontDefault());
    minimizeButton.addListener(SWT.Selection, e -> shell.dispose());

    Button closeButton = new Button(buttonComposite, SWT.PUSH);
    closeButton.setText(BaseMessages.getString(PKG, "LlmAssistant.Button.Close"));
    FormData fdClose = new FormData();
    fdClose.right = new FormAttachment(minimizeButton, -PropsUi.getMargin());
    fdClose.top = new FormAttachment(0, 0);
    fdClose.width = 100;
    closeButton.setLayoutData(fdClose);
    PropsUi.setLook(closeButton);
    closeButton.setFont(GuiResource.getInstance().getFontDefault());
    closeButton.addListener(SWT.Selection, e -> shell.dispose());

    shell.setDefaultButton(sendButton);
  }

  private void sendMessage() {
    String question = inputArea.getText().trim();
    if (question.isEmpty()) {
      return;
    }
    if (!LlmAssistantConfig.getInstance().isAvailable()) {
      appendAssistantMessage(BaseMessages.getString(PKG, "LlmAssistant.Message.NotConfigured"));
      return;
    }

    appendUserMessage(question);
    history.add(new LlmClient.ChatMessage("user", question));
    trimHistory();

    inputArea.setText("");
    setBusy(true);
    int thinkingStart = getCharCount();
    appendAssistantMessage(BaseMessages.getString(PKG, "LlmAssistant.Message.Thinking"));

    Display display = shell.getDisplay();
    List<LlmClient.ChatMessage> snapshot = List.copyOf(history);
    String errorPrefix = BaseMessages.getString(PKG, "LlmAssistant.Message.Error", "");

    Thread worker =
        new Thread(
            () -> {
              String reply;
              try {
                reply = client.chat(snapshot);
              } catch (Exception e) {
                reply = BaseMessages.getString(PKG, "LlmAssistant.Message.Error", e.getMessage());
              }
              final String finalReply = reply;
              display.asyncExec(
                  () -> {
                    if (chatArea.isDisposed()) {
                      return;
                    }
                    // Replace the "Thinking..." placeholder with the real reply.
                    String current = chatArea.getText();
                    chatArea.setText(current.substring(0, thinkingStart));
                    appendAssistantMessage(finalReply);
                    if (!finalReply.startsWith(errorPrefix)) {
                      history.add(new LlmClient.ChatMessage("assistant", finalReply));
                      trimHistory();
                    }
                    setBusy(false);
                    inputArea.setFocus();
                  });
            },
            "Hop-LlmAssistant");
    worker.setDaemon(true);
    worker.start();
  }

  private void appendUserMessage(String text) {
    appendMessage(BaseMessages.getString(PKG, "LlmAssistant.Message.You"), text);
  }

  private void appendAssistantMessage(String text) {
    appendMessage(BaseMessages.getString(PKG, "LlmAssistant.Message.Assistant"), text);
  }

  private void appendMessage(String speaker, String text) {
    StringBuilder builder = new StringBuilder();
    if (getCharCount() > 0) {
      builder.append("\n");
    }
    builder.append(speaker).append(":\n").append(text).append("\n");
    chatArea.append(builder.toString());
    chatArea.setSelection(getCharCount());
    chatArea.showSelection();
  }

  private int getCharCount() {
    return chatArea.getText().length();
  }

  private void trimHistory() {
    while (history.size() > MAX_HISTORY_TURNS * 2) {
      history.remove(0);
    }
  }

  private void setBusy(boolean value) {
    busy = value;
    if (sendButton != null && !sendButton.isDisposed()) {
      sendButton.setEnabled(!value);
    }
    if (inputArea != null && !inputArea.isDisposed()) {
      inputArea.setEnabled(!value);
    }
  }
}
