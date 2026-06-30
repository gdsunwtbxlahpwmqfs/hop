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

import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.gui.GuiResource;
import org.apache.hop.ui.core.widget.svg.SvgLabelFacade;
import org.apache.hop.ui.util.EnvironmentUtils;
import org.apache.hop.ui.util.SwtSvgImageUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * A circular "doctor" icon button pinned to the bottom-right corner of the main window. Clicking it
 * opens the {@link LlmAssistantDialog}; closing or minimising the dialog brings the user straight
 * back to this icon. The button is created whenever the assistant is enabled via the {@code
 * HOP_LLM_ENABLED} environment variable (see {@link LlmAssistantConfig#isEnabled()}).
 */
public class LlmAssistantFloatingButton {

  private static final Class<?> PKG = LlmAssistantDialog.class;

  private static final String ICON_PATH = "ui/images/assistant.svg";
  private static final int ICON_SIZE = 30;
  private static final int BUTTON_SIZE = 48;

  private final Shell shell;
  private Composite button;

  public LlmAssistantFloatingButton(Shell shell) {
    this.shell = shell;
  }

  /**
   * Creates the floating button as a child of {@code parent} (the main Hop GUI composite), pinned
   * to the bottom-right corner. Does nothing if the assistant is not available.
   *
   * @return {@code true} if the button was created.
   */
  public boolean create(Composite parent) {
    // Show the button whenever the assistant is enabled. Even without a configured API URL the
    // dialog opens and guides the user to complete the configuration.
    if (!LlmAssistantConfig.getInstance().isEnabled()) {
      return false;
    }
    if (parent == null || parent.isDisposed()) {
      return false;
    }

    button = new Composite(parent, SWT.NONE);
    button.setToolTipText(BaseMessages.getString(PKG, "LlmAssistant.Button.Tooltip"));
    Color normalBg = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);
    Color hoverBg = GuiResource.getInstance().getColorGray();
    button.setBackground(normalBg);

    GridLayout gl = new GridLayout(1, false);
    gl.marginWidth = 0;
    gl.marginHeight = 0;
    button.setLayout(gl);

    Label icon = new Label(button, SWT.NONE);
    icon.setBackground(normalBg);
    icon.setToolTipText(BaseMessages.getString(PKG, "LlmAssistant.Button.Tooltip"));
    icon.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

    if (EnvironmentUtils.getInstance().isWeb()) {
      SvgLabelFacade.setData("llm-assistant-fab", icon, ICON_PATH, ICON_SIZE);
    } else {
      Display display = parent.getDisplay();
      icon.setImage(
          SwtSvgImageUtil.getImageAsResource(display, ICON_PATH)
              .getAsBitmapForSize(display, ICON_SIZE, ICON_SIZE));
    }

    // Pin to bottom-right corner of the main composite, floating over the perspectives.
    int size = (int) (BUTTON_SIZE * PropsUi.getNativeZoomFactor());
    FormData fd = new FormData();
    fd.right = new FormAttachment(100, -PropsUi.getMargin());
    fd.bottom = new FormAttachment(100, -PropsUi.getMargin());
    fd.width = size;
    fd.height = size;
    button.setLayoutData(fd);

    // Hover highlight + click handling (delegated from the label too).
    Runnable openDialog =
        () -> {
          if (shell == null || shell.isDisposed()) {
            return;
          }
          // Disable the button while the modal dialog is open so focus returns cleanly afterwards.
          button.setEnabled(false);
          try {
            new LlmAssistantDialog(shell).open();
          } finally {
            if (button != null && !button.isDisposed()) {
              button.setEnabled(true);
            }
          }
        };

    button.addListener(SWT.MouseEnter, e -> button.setBackground(hoverBg));
    button.addListener(SWT.MouseExit, e -> button.setBackground(normalBg));
    button.addListener(SWT.MouseDown, e -> openDialog.run());
    icon.addListener(SWT.MouseEnter, e -> button.setBackground(hoverBg));
    icon.addListener(SWT.MouseExit, e -> button.setBackground(normalBg));
    icon.addListener(SWT.MouseDown, e -> openDialog.run());

    // Make sure the button paints on top of the perspective composites.
    button.moveAbove(null);
    return true;
  }

  /** Disposes the floating button if it exists. */
  public void dispose() {
    if (button != null && !button.isDisposed()) {
      button.dispose();
    }
  }
}
