/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.ui.hopgui.dialog;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.gui.GuiResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UploadDialog extends Dialog {
  private static final Class<?> PKG = UploadDialog.class;

  private Shell shell;
  private Browser browser;
  private String dest;
  private Runnable onCloseCallback;
  private boolean functionsRegistered;

  public UploadDialog(Shell parent, String dest) {
    super(parent, SWT.NONE);
    this.dest = dest;
  }

  public UploadDialog(Shell parent, String dest, Runnable onCloseCallback) {
    super(parent, SWT.NONE);
    this.dest = dest;
    this.onCloseCallback = onCloseCallback;
  }

  public void open() {
    Shell parent = getParent();
    Display display = parent.getDisplay();

    shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.SHEET | SWT.RESIZE);
    shell.setText(BaseMessages.getString(PKG, "UploadDialog.Title"));
    shell.setImage(GuiResource.getInstance().getImageHopUi());
    shell.setSize(960, 640);
    shell.setMinimumSize(720, 480);

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = 0;
    formLayout.marginHeight = 0;
    shell.setLayout(formLayout);
    PropsUi.setLook(shell);

    browser = new Browser(shell, SWT.NONE);
    FormData fdBrowser = new FormData();
    fdBrowser.top = new FormAttachment(0, 0);
    fdBrowser.left = new FormAttachment(0, 0);
    fdBrowser.right = new FormAttachment(100, 0);
    fdBrowser.bottom = new FormAttachment(100, 0);
    browser.setLayoutData(fdBrowser);

    // In RAP, setUrl() causes the browser page to reload. BrowserFunctions registered
    // before or during the page transition get disposed by the RAP lifecycle. We must
    // wait until the page has fully loaded before registering them.
    browser.addProgressListener(
        new ProgressAdapter() {
          @Override
          public void completed(ProgressEvent event) {
            if (!functionsRegistered && !browser.isDisposed()) {
              functionsRegistered = true;
              registerBrowserFunctions();
            }
          }
        });

    String safeDest = dest != null ? dest : "";
    String url = "/upload/?dest=" + URLEncoder.encode(safeDest, StandardCharsets.UTF_8);
    browser.setUrl(url);

    shell.addShellListener(
        new ShellAdapter() {
          @Override
          public void shellClosed(ShellEvent e) {
            dispose();
            if (onCloseCallback != null) {
              onCloseCallback.run();
            }
          }
        });

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  private void registerBrowserFunctions() {
    // Called when a file upload completes — refresh the file tree but do NOT close the dialog.
    new BrowserFunction(browser, "onHopUploadComplete") {
      @Override
      public Object function(Object[] arguments) {
        if (onCloseCallback != null) {
          onCloseCallback.run();
        }
        return null;
      }
    };

    // Called when the user clicks the Close button — closes the dialog.
    new BrowserFunction(browser, "onHopUploadClose") {
      @Override
      public Object function(Object[] arguments) {
        closeDialog();
        return null;
      }
    };
  }

  /**
   * Closes the dialog asynchronously to avoid disposing the Browser/BrowserFunction while RAP is
   * still processing the current browser callback (which causes "BrowserFunction is disposed"
   * SWTError).
   */
  private void closeDialog() {
    Display display = shell.getDisplay();
    display.asyncExec(
        () -> {
          if (shell != null && !shell.isDisposed()) {
            shell.close();
          }
        });
  }

  public void dispose() {
    if (browser != null && !browser.isDisposed()) {
      browser.dispose();
    }
    if (shell != null && !shell.isDisposed()) {
      shell.dispose();
    }
  }

  public void updateDest(String newDest) {
    this.dest = newDest;
    if (browser != null && !browser.isDisposed()) {
      functionsRegistered = false;
      String url = "/upload/?dest=" + URLEncoder.encode(newDest, StandardCharsets.UTF_8);
      browser.setUrl(url);
    }
  }

  public String getDest() {
    return dest;
  }
}
