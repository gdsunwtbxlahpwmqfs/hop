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

package org.apache.hop.ui.hopgui.terminal;

import java.util.UUID;
import org.apache.hop.core.logging.LogChannel;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.remote.AbstractOperationHandler;
import org.eclipse.rap.rwt.remote.Connection;
import org.eclipse.rap.rwt.remote.RemoteObject;
import org.eclipse.rap.rwt.widgets.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

/** RAP (web) implementation of ITerminalWidget using xterm.js in the browser. */
public class RapTerminalWidget implements ITerminalWidget {

  private static final LogChannel log = new LogChannel("RapTerminal");

  private static final String REMOTE_TYPE = "hop.Terminal";

  private final Composite composite;
  private final String ptyId;
  private final String shellPath;
  private final String workingDirectory;
  private RemoteObject remoteObject;
  private boolean connected;

  public RapTerminalWidget(
      Composite parent, String shellPath, String workingDirectory, int fontSizePercent) {
    this.shellPath = shellPath;
    this.workingDirectory = workingDirectory;
    this.ptyId = UUID.randomUUID().toString();

    composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new FormLayout());
    FormData fd = new FormData();
    fd.left = new FormAttachment(0, 0);
    fd.right = new FormAttachment(100, 0);
    fd.top = new FormAttachment(0, 0);
    fd.bottom = new FormAttachment(100, 0);
    composite.setLayoutData(fd);

    composite.addListener(
        SWT.Dispose,
        event -> {
          try {
            if (remoteObject != null) {
              remoteObject.destroy();
              remoteObject = null;
            }
          } catch (Exception ignored) {
          }
        });

    try {
      Connection connection = RWT.getUISession().getConnection();
      remoteObject = connection.createRemoteObject(REMOTE_TYPE);
      remoteObject.set("parent", WidgetUtil.getId(composite));
      remoteObject.set("ptyId", ptyId);
      remoteObject.set("shellPath", shellPath);
      remoteObject.set("workingDirectory", workingDirectory);
      remoteObject.set("fontSizePercent", fontSizePercent);

      remoteObject.setHandler(
          new AbstractOperationHandler() {
            @Override
            public void handleNotify(String event, JsonObject properties) {
              if ("terminalError".equals(event)) {
                String msg = properties != null ? properties.toString() : "unknown";
                log.logError("Terminal client error: " + msg);
              }
            }
          });

      connected = true;
    } catch (Exception e) {
      log.logError("Failed to create RAP terminal remote object", e);
      connected = false;
    }
  }

  @Override
  public Composite getTerminalComposite() {
    return composite;
  }

  @Override
  public void dispose() {
    try {
      if (remoteObject != null) {
        remoteObject.destroy();
        remoteObject = null;
      }
    } catch (Exception ignored) {
    }
    if (!composite.isDisposed()) {
      composite.dispose();
    }
    connected = false;
  }

  @Override
  public boolean isConnected() {
    return connected;
  }

  @Override
  public String getShellPath() {
    return shellPath;
  }

  @Override
  public String getWorkingDirectory() {
    return workingDirectory;
  }

  @Override
  public StyledText getOutputText() {
    // xterm.js renders in the browser; no server-side StyledText
    return null;
  }

  @Override
  public String getTerminalType() {
    return "xterm.js";
  }

  @Override
  public void setFontScalePercent(int percent) {
    if (remoteObject != null) {
      remoteObject.set("fontSizePercent", Math.clamp(percent, 50, 200));
    }
  }
}
