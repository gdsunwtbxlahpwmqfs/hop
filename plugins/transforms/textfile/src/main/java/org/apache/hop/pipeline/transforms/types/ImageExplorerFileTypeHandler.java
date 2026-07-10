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
 *
 */

package org.apache.hop.pipeline.transforms.types;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import org.apache.hop.core.Const;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.core.vfs.HopVfs;
import org.apache.hop.ui.hopgui.HopGui;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerFile;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerPerspective;
import org.apache.hop.ui.hopgui.perspective.explorer.file.types.base.BaseExplorerFileTypeHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

/** Renders an image file (.png/.jpg/.gif/.bmp) in the explorer using a Browser widget. */
public class ImageExplorerFileTypeHandler extends BaseExplorerFileTypeHandler {

  private Browser wBrowser;

  public ImageExplorerFileTypeHandler(
      HopGui hopGui, ExplorerPerspective perspective, ExplorerFile explorerFile) {
    super(hopGui, perspective, explorerFile);
  }

  @Override
  public void renderFile(Composite composite) {
    wBrowser = new Browser(composite, SWT.NONE);
    FormData fdBrowser = new FormData();
    fdBrowser.left = new FormAttachment(0, 0);
    fdBrowser.right = new FormAttachment(100, 0);
    fdBrowser.top = new FormAttachment(0, 0);
    fdBrowser.bottom = new FormAttachment(100, 0);
    wBrowser.setLayoutData(fdBrowser);

    reload();
  }

  @Override
  public void reload() {
    try {
      String filename = explorerFile.getFilename();
      if (!HopVfs.fileExists(filename)) {
        showError("File not found: " + filename);
        return;
      }

      String mimeType = getMimeType(filename);
      try (InputStream input = HopVfs.getInputStream(filename, getVariables())) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int n;
        while ((n = input.read(buffer)) > 0) {
          baos.write(buffer, 0, n);
        }
        String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
        String dataUri = "data:" + mimeType + ";base64," + base64;

        String html =
            "<html><head><meta charset=\"UTF-8\"><style>"
                + "html,body{margin:0;padding:0;height:100%;background:#fff;display:flex;"
                + "align-items:center;justify-content:center;overflow:hidden}"
                + "img{max-width:100%;max-height:100%;object-fit:contain;"
                + "image-rendering:auto;box-shadow:0 2px 10px rgba(0,0,0,.15)}"
                + "</style></head><body>"
                + "<img src=\""
                + dataUri
                + "\" alt=\""
                + escapeAttr(filename)
                + "\">"
                + "</body></html>";
        wBrowser.setText(html);
      }
      clearChanged();
    } catch (Exception e) {
      LogChannel.UI.logError("Error loading image '" + explorerFile.getFilename() + "'", e);
      showError("Error loading image: " + Const.NVL(e.getMessage(), "Unknown error"));
    }
  }

  private void showError(String message) {
    wBrowser.setText(
        "<html><body style=\"background:#fff;color:#333;font-family:sans-serif;"
            + "display:flex;align-items:center;justify-content:center;height:100vh;margin:0\">"
            + "<p>"
            + escapeAttr(message)
            + "</p></body></html>");
  }

  private static String getMimeType(String filename) {
    String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    switch (ext) {
      case "png":
        return "image/png";
      case "jpg":
      case "jpeg":
        return "image/jpeg";
      case "gif":
        return "image/gif";
      case "bmp":
        return "image/bmp";
      case "webp":
        return "image/webp";
      case "ico":
        return "image/x-icon";
      default:
        return "image/png";
    }
  }

  private static String escapeAttr(String text) {
    if (text == null) return "";
    return text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;");
  }
}
