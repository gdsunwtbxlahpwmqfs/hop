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

package org.apache.hop.ui.hopgui.perspective.explorer.file;

import java.io.InputStream;
import org.apache.hop.core.Const;
import org.apache.hop.core.exception.HopException;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.core.vfs.HopVfs;
import org.apache.hop.ui.hopgui.HopGui;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerFile;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerPerspective;
import org.apache.hop.ui.hopgui.perspective.explorer.file.types.base.BaseExplorerFileTypeHandler;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

/**
 * Renders a Word document (.doc/.docx) in the explorer by parsing it directly with Apache POI and
 * generating formatted HTML (headings, bold, lists, tables) displayed in a Browser widget. This
 * avoids the Tika SAX/TransformerFactory classloader conflict present in Java 9+ module systems.
 */
public class TikaExplorerFileTypeHandler extends BaseExplorerFileTypeHandler {

  private Browser wBrowser;

  public TikaExplorerFileTypeHandler(
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
      String html = buildHtml();
      wBrowser.setText(html);
      clearChanged();
    } catch (Exception e) {
      LogChannel.UI.logError("Error reading document '" + explorerFile.getFilename() + "'", e);
      wBrowser.setText(
          "<html><body><h1>Error loading document</h1><p>"
              + Const.NVL(e.getMessage(), "Unknown error")
              + "</p></body></html>");
    }
  }

  private String buildHtml() throws Exception {
    String filename = explorerFile.getFilename();
    boolean isDocx = filename.toLowerCase().endsWith(".docx");

    StringBuilder body = new StringBuilder(8192);

    try (InputStream input = HopVfs.getInputStream(filename, getVariables())) {
      if (isDocx) {
        renderDocx(input, body);
      } else {
        renderDoc(input, body);
      }
    }

    return "<html><head><meta charset=\"UTF-8\"><style>"
        + "*{background:#fff !important;color:#222 !important;}"
        + "body{font-family:'Helvetica Neue',Arial,sans-serif !important;padding:20px !important;"
        + "max-width:900px !important;margin:auto !important;line-height:1.6 !important;}"
        + "h1{font-size:1.8em !important;border-bottom:2px solid #eee !important;padding-bottom:4px !important;margin-top:1em !important;}"
        + "h2{font-size:1.5em !important;}h3{font-size:1.25em !important;}h4,h5,h6{font-size:1.1em !important;}"
        + "table{border-collapse:collapse !important;margin:8px 0 !important;width:100% !important;}"
        + "td,th{border:1px solid #ccc !important;padding:4px 10px !important;font-size:14px !important;}"
        + "th{background:#f0f0f0 !important;font-weight:bold !important;}"
        + "p{margin:6px 0 !important;}"
        + "strong{font-weight:bold !important;}"
        + "em{font-style:italic !important;}"
        + "</style></head><body>"
        + body
        + "</body></html>";
  }

  /** Parse a .docx file with XWPF, preserving headings, bold runs and tables. */
  private void renderDocx(InputStream input, StringBuilder body) throws Exception {
    try (XWPFDocument doc = new XWPFDocument(input)) {
      for (IBodyElement element : doc.getBodyElements()) {
        switch (element.getElementType()) {
          case PARAGRAPH:
            renderParagraph((XWPFParagraph) element, body);
            break;
          case TABLE:
            renderTable((XWPFTable) element, body);
            break;
          default:
            break;
        }
      }
    }
  }

  private void renderParagraph(XWPFParagraph para, StringBuilder body) {
    StringBuilder text = new StringBuilder();
    for (XWPFRun run : para.getRuns()) {
      String runText = run.getText(0);
      if (runText == null) continue;
      if (run.isBold()) {
        text.append("<strong>").append(escapeHtml(runText)).append("</strong>");
      } else if (run.isItalic()) {
        text.append("<em>").append(escapeHtml(runText)).append("</em>");
      } else {
        text.append(escapeHtml(runText));
      }
    }

    String content = text.toString().trim();
    if (content.isEmpty()) {
      body.append("<p>&nbsp;</p>");
      return;
    }

    // Detect heading level from style id (e.g. "Heading1", "1", "2")
    int level = headingLevel(para.getStyleID());
    if (level > 0) {
      body.append("<h")
          .append(level)
          .append(">")
          .append(content)
          .append("</h")
          .append(level)
          .append(">");
    } else {
      body.append("<p>").append(content).append("</p>");
    }
  }

  private void renderTable(XWPFTable table, StringBuilder body) {
    body.append("<table>");
    for (XWPFTableRow row : table.getRows()) {
      body.append("<tr>");
      for (XWPFTableCell cell : row.getTableCells()) {
        String cellText = cell.getText();
        body.append("<td>").append(escapeHtml(Const.NVL(cellText, ""))).append("</td>");
      }
      body.append("</tr>");
    }
    body.append("</table>");
  }

  /**
   * Parse a legacy .doc file with HWPF. Formatting is limited so we extract paragraph text and
   * preserve line structure.
   */
  private void renderDoc(InputStream input, StringBuilder body) throws Exception {
    try (HWPFDocument doc = new HWPFDocument(input);
        WordExtractor extractor = new WordExtractor(doc)) {
      for (String paragraph : extractor.getParagraphText()) {
        String text = paragraph.trim();
        if (!text.isEmpty()) {
          body.append("<p>").append(escapeHtml(text)).append("</p>");
        }
      }
    }
  }

  /** Returns heading level 1-6 if the style id indicates a heading, otherwise 0. */
  private static int headingLevel(String styleId) {
    if (styleId == null || styleId.isEmpty()) return 0;
    String upper = styleId.toUpperCase();
    if (upper.startsWith("HEADING")) {
      String num = upper.substring("HEADING".length());
      return parseLevel(num);
    }
    // Numeric style ids 1-6 often map to headings
    return parseLevel(styleId);
  }

  private static int parseLevel(String num) {
    try {
      int level = Integer.parseInt(num.trim());
      return (level >= 1 && level <= 6) ? level : 0;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  private static String escapeHtml(String text) {
    if (text == null) return "";
    return text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;");
  }

  @Override
  public void save() throws HopException {
    throw new HopException("Documents opened as read-only views cannot be saved.");
  }

  @Override
  public void saveAs(String filename) throws HopException {
    throw new HopException("Documents opened as read-only views cannot be saved.");
  }
}
