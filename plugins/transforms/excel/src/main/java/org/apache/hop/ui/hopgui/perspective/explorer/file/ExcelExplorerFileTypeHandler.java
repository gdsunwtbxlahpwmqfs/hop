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
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.hopgui.HopGui;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerFile;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerPerspective;
import org.apache.hop.ui.hopgui.perspective.explorer.file.types.base.BaseExplorerFileTypeHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

/**
 * Renders an Excel file (.xls/.xlsx) in the explorer as a high-quality spreadsheet view in a
 * Browser widget: frozen header row &amp; row-number column, zebra striping, numeric
 * right-alignment, multi-sheet tabs with JavaScript switching.
 */
public class ExcelExplorerFileTypeHandler extends BaseExplorerFileTypeHandler {

  private static final int MAX_ROWS = 5000;

  private Browser wBrowser;

  public ExcelExplorerFileTypeHandler(
      HopGui hopGui, ExplorerPerspective perspective, ExplorerFile explorerFile) {
    super(hopGui, perspective, explorerFile);
  }

  @Override
  public void renderFile(Composite composite) {
    wBrowser = new Browser(composite, SWT.NONE);
    PropsUi.setLook(wBrowser);
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
      LogChannel.UI.logError("Error reading Excel file '" + explorerFile.getFilename() + "'", e);
      wBrowser.setText(
          "<html><body><h1>Error loading Excel file</h1><p>"
              + Const.NVL(e.getMessage(), "Unknown error")
              + "</p></body></html>");
    }
  }

  private String buildHtml() throws Exception {
    StringBuilder html = new StringBuilder(16384);
    html.append(
        "<html><head><meta charset=\"UTF-8\"><style>"
            + "*{box-sizing:border-box;}"
            + "body{font-family:'Segoe UI',Helvetica,Arial,sans-serif;margin:0;padding:0;"
            + "background:#fafafa;height:100vh;display:flex;flex-direction:column;}"
            + ".toolbar{background:#1a73e8;color:#fff;padding:6px 12px;font-size:13px;"
            + "display:flex;align-items:center;gap:10px;}"
            + ".tabs{background:#e8eaed;display:flex;overflow-x:auto;border-bottom:1px solid #bbb;}"
            + ".tab{padding:6px 16px;cursor:pointer;font-size:13px;color:#3c4043;"
            + "border:1px solid transparent;border-bottom:none;white-space:nowrap;}"
            + ".tab.active{background:#fff;border-color:#bbb;color:#1a73e8;font-weight:600;}"
            + ".tab:hover:not(.active){background:#dadce0;}"
            + ".viewport{flex:1;overflow:auto;position:relative;}"
            + ".sheet{display:none;}"
            + ".sheet.active{display:block;}"
            + "table{border-collapse:separate;border-spacing:0;}"
            + "th,td{border-right:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0;"
            + "padding:3px 8px;font-size:12px;white-space:nowrap;background:#fff;}"
            + "th.rn,td.rn{background:#f8f9fa;color:#80868b;text-align:center;"
            + "font-size:11px;position:sticky;left:0;z-index:2;border-right:2px solid #e0e0e0;}"
            + "thead th{position:sticky;top:0;z-index:3;background:#f1f3f4;font-weight:600;"
            + "color:#3c4043;border-bottom:2px solid #dadce0;}"
            + "thead th.rn{z-index:4;}"
            + "tbody tr:nth-child(even) td{background:#f8f9fa;}"
            + "tbody tr:nth-child(even) td.rn{background:#eef0f2;}"
            + "td.num{text-align:right;font-variant-numeric:tabular-nums;}"
            + ".truncated{color:#999;font-style:italic;padding:6px 12px;}"
            + "</style></head><body>");

    try (InputStream input = HopVfs.getInputStream(getFilename(), getVariables());
        Workbook workbook = WorkbookFactory.create(input)) {

      int sheetCount = workbook.getNumberOfSheets();
      String fname = explorerFile.getFilename();
      int slash = Math.max(fname.lastIndexOf('/'), fname.lastIndexOf('\\'));
      String displayName = slash >= 0 ? fname.substring(slash + 1) : fname;

      html.append("<div class=\"toolbar\"><b>").append(escapeHtml(displayName)).append("</b>");
      if (sheetCount > 1) {
        html.append("<span style=\"opacity:.8\">").append(sheetCount).append(" sheets</span>");
      }
      html.append("</div>");

      // Tab bar
      if (sheetCount > 1) {
        html.append("<div class=\"tabs\">");
        for (int i = 0; i < sheetCount; i++) {
          html.append("<div class=\"tab")
              .append(i == 0 ? " active" : "")
              .append("\" onclick=\"showSheet(")
              .append(i)
              .append(")\" id=\"tab")
              .append(i)
              .append("\">")
              .append(escapeHtml(workbook.getSheetName(i)))
              .append("</div>");
        }
        html.append("</div>");
      }

      html.append("<div class=\"viewport\">");
      DataFormatter formatter = new DataFormatter();
      for (int s = 0; s < sheetCount; s++) {
        Sheet sheet = workbook.getSheetAt(s);
        html.append("<div class=\"sheet")
            .append(s == 0 ? " active" : "")
            .append("\" id=\"sheet")
            .append(s)
            .append("\">");
        html.append("<table><thead><tr>");
        html.append("<th class=\"rn\"></th>"); // top-left corner

        // Determine max column count
        int maxCols = 0;
        for (Row row : sheet) {
          maxCols = Math.max(maxCols, row.getLastCellNum());
        }
        for (int c = 0; c < maxCols; c++) {
          html.append("<th>").append(columnLetter(c)).append("</th>");
        }
        html.append("</tr></thead><tbody>");

        int rowIdx = 0;
        for (Row row : sheet) {
          if (rowIdx >= MAX_ROWS) {
            html.append("<tr><td colspan=\"")
                .append(maxCols + 1)
                .append("\" class=\"truncated\">Showing first ")
                .append(MAX_ROWS)
                .append(" of ")
                .append(sheet.getLastRowNum() + 1)
                .append(" rows</td></tr>");
            break;
          }
          html.append("<tr><td class=\"rn\">").append(rowIdx + 1).append("</td>");
          for (int c = 0; c < maxCols; c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            boolean isNumeric = cell != null && cell.getCellType() == CellType.NUMERIC;
            html.append("<td").append(isNumeric ? " class=\"num\"" : "").append(">");
            if (cell != null) {
              html.append(escapeHtml(formatter.formatCellValue(cell)));
            }
            html.append("</td>");
          }
          html.append("</tr>");
          rowIdx++;
        }
        html.append("</tbody></table></div>");
      }
      html.append("</div>");
    }

    html.append(
        "<script>"
            + "function showSheet(i){"
            + "document.querySelectorAll('.sheet').forEach(s=>s.classList.remove('active'));"
            + "document.querySelectorAll('.tab').forEach(t=>t.classList.remove('active'));"
            + "document.getElementById('sheet'+i).classList.add('active');"
            + "var t=document.getElementById('tab'+i);if(t)t.classList.add('active');"
            + "}</script>");
    html.append("</body></html>");
    return html.toString();
  }

  /** Convert a zero-based column index to an Excel column letter (0->A, 27->AB). */
  private static String columnLetter(int index) {
    StringBuilder sb = new StringBuilder();
    int n = index;
    do {
      sb.insert(0, (char) ('A' + n % 26));
      n = n / 26 - 1;
    } while (n >= 0);
    return sb.toString();
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
    throw new HopException("Excel files opened as read-only views cannot be saved.");
  }

  @Override
  public void saveAs(String filename) throws HopException {
    throw new HopException("Excel files opened as read-only views cannot be saved.");
  }
}
