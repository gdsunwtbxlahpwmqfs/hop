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

package org.apache.hop.ui.hopgui.perspective.explorer.file.types.excel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.hop.core.Const;
import org.apache.hop.core.exception.HopException;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.core.vfs.HopVfs;
import org.apache.hop.ui.hopgui.HopGui;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerFile;
import org.apache.hop.ui.hopgui.perspective.explorer.ExplorerPerspective;
import org.apache.hop.ui.hopgui.perspective.explorer.file.types.base.BaseExplorerFileTypeHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

public class ExcelExplorerFileTypeHandler extends BaseExplorerFileTypeHandler {

  private static final int MAX_ROWS = 1000;
  private static final int MAX_COLS = 100;

  private Browser wBrowser;

  public ExcelExplorerFileTypeHandler(
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
      LogChannel.UI.logError("Error reading Excel file '" + explorerFile.getFilename() + "'", e);
      wBrowser.setText(
          "<html><body><h1>Error loading Excel file</h1><p>"
              + Const.NVL(e.getMessage(), "Unknown error")
              + "</p></body></html>");
    }
  }

  private String buildHtml() throws Exception {
    String filename = explorerFile.getFilename();

    byte[] fileBytes;
    try (InputStream input = HopVfs.getInputStream(filename, getVariables())) {
      fileBytes = input.readAllBytes();
    }

    String head =
        new String(fileBytes, 0, Math.min(fileBytes.length, 512), StandardCharsets.UTF_8)
            .trim()
            .toLowerCase();
    boolean isHtml =
        head.startsWith("<table") || head.startsWith("<html") || head.startsWith("<!doctype html");

    StringBuilder tabsHtml = new StringBuilder(1024);
    StringBuilder contentHtml = new StringBuilder(8192);

    if (isHtml) {
      renderHtmlTable(fileBytes, tabsHtml, contentHtml);
    } else {
      try (ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes);
          Workbook workbook = WorkbookFactory.create(bais)) {
        renderWorkbook(workbook, tabsHtml, contentHtml);
      }
    }

    return "<html><head><meta charset=\"UTF-8\"><style>"
        + "*{background:#fff !important;color:#222 !important;}"
        + "body{font-family:'Helvetica Neue',Arial,sans-serif !important;padding:20px !important;"
        + "max-width:1200px !important;margin:auto !important;line-height:1.4 !important;}"
        + ".tab-container{margin-top:16px !important;}"
        + ".tab-buttons{display:flex !important;gap:4px !important;border-bottom:2px solid #ddd !important;padding-bottom:0 !important;margin-bottom:0 !important;}"
        + ".tab-btn{background:#f5f5f5 !important;border:2px solid #ddd !important;border-bottom:none !important;"
        + "padding:8px 16px !important;font-size:13px !important;cursor:pointer !important;border-radius:4px 4px 0 0 !important;"
        + "color:#666 !important;transition:all 0.2s !important;}"
        + ".tab-btn:hover{background:#e8e8e8 !important;color:#333 !important;}"
        + ".tab-btn.active{background:#fff !important;border-color:#0E3A5A !important;color:#0E3A5A !important;font-weight:bold !important;}"
        + ".tab-content{display:none !important;padding:16px !important;border:2px solid #ddd !important;border-top:none !important;border-radius:0 4px 4px 4px !important;}"
        + ".tab-content.active{display:block !important;}"
        + "table{border-collapse:collapse !important;margin:8px 0 !important;width:100% !important;"
        + "display:block !important;overflow-x:auto !important;white-space:nowrap !important;}"
        + "td,th{border:1px solid #ccc !important;padding:4px 8px !important;font-size:13px !important;"
        + "min-width:80px !important;text-align:left !important;vertical-align:top !important;}"
        + "th{background:#f5f5f5 !important;font-weight:bold !important;color:#333 !important;}"
        + "tr:nth-child(even){background:#fafafa !important;}"
        + ".empty-cell{color:#999 !important;font-style:italic !important;}"
        + ".sheet-info{font-size:12px !important;color:#888 !important;margin-bottom:8px !important;}"
        + "</style></head><body>"
        + "<div class=\"tab-container\">"
        + tabsHtml
        + contentHtml
        + "</div>"
        + "<script>"
        + "function showTab(tabId){"
        + "  document.querySelectorAll('.tab-btn').forEach(function(b){b.classList.remove('active');});"
        + "  document.querySelectorAll('.tab-content').forEach(function(c){c.classList.remove('active');});"
        + "  document.getElementById('btn-'+tabId).classList.add('active');"
        + "  document.getElementById('tab-'+tabId).classList.add('active');"
        + "}"
        + "</script>"
        + "</body></html>";
  }

  private void renderHtmlTable(
      byte[] fileBytes, StringBuilder tabsHtml, StringBuilder contentHtml) {
    String htmlContent = new String(fileBytes, StandardCharsets.UTF_8);

    int tableCount = 0;
    int searchFrom = 0;
    int maxRows = MAX_ROWS;

    tabsHtml.append("<div class=\"tab-buttons\">");

    int tableStart;
    while ((tableStart = htmlContent.toLowerCase().indexOf("<table", searchFrom)) >= 0) {
      int tableEnd = htmlContent.toLowerCase().indexOf("</table>", tableStart);
      if (tableEnd < 0) {
        tableEnd = htmlContent.length();
      } else {
        tableEnd += "</table>".length();
      }

      String tableHtml = htmlContent.substring(tableStart, tableEnd);
      String tabId = "sheet-" + tableCount;
      String tabLabel = (tableCount == 0) ? "Sheet1" : "Sheet" + (tableCount + 1);
      String activeClass = (tableCount == 0) ? " active" : "";

      tabsHtml
          .append("<button id=\"btn-")
          .append(tabId)
          .append("\" class=\"tab-btn")
          .append(activeClass)
          .append("\" onclick=\"showTab('")
          .append(tabId)
          .append("')\">")
          .append(escapeHtml(tabLabel))
          .append("</button>");

      contentHtml
          .append("<div id=\"tab-")
          .append(tabId)
          .append("\" class=\"tab-content")
          .append(activeClass)
          .append("\">");

      String truncated = truncateHtmlTableRows(tableHtml, maxRows);
      contentHtml.append(truncated);

      contentHtml.append("</div>");

      tableCount++;
      searchFrom = tableEnd;
    }

    if (tableCount == 0) {
      contentHtml.append("<p class=\"empty-cell\">No table data found in this HTML file.</p>");
    }

    tabsHtml.append("</div>");
  }

  private String truncateHtmlTableRows(String tableHtml, int maxRows) {
    String lower = tableHtml.toLowerCase();
    int rowCount = 0;
    int searchFrom = 0;
    int trPos;
    while ((trPos = lower.indexOf("<tr", searchFrom)) >= 0) {
      rowCount++;
      if (rowCount > maxRows) {
        int trEnd = lower.indexOf("</tr>", trPos);
        if (trEnd < 0) trEnd = tableHtml.length();
        return tableHtml.substring(0, trPos)
            + "<tr><td><em>... truncated at "
            + maxRows
            + " rows</em></td></tr></table>";
      }
      searchFrom = trPos + 4;
    }
    return tableHtml;
  }

  private void renderWorkbook(
      Workbook workbook, StringBuilder tabsHtml, StringBuilder contentHtml) {
    int numberOfSheets = workbook.getNumberOfSheets();

    tabsHtml.append("<div class=\"tab-buttons\">");
    for (int i = 0; i < numberOfSheets; i++) {
      String sheetName = workbook.getSheetName(i);
      String tabId = "sheet-" + i;
      String activeClass = (i == 0) ? " active" : "";
      tabsHtml
          .append("<button id=\"btn-")
          .append(tabId)
          .append("\" class=\"tab-btn")
          .append(activeClass)
          .append("\" onclick=\"showTab('")
          .append(tabId)
          .append("')\">")
          .append(escapeHtml(sheetName))
          .append("</button>");
    }
    tabsHtml.append("</div>");

    for (int i = 0; i < numberOfSheets; i++) {
      Sheet sheet = workbook.getSheetAt(i);
      String sheetName = workbook.getSheetName(i);
      String tabId = "sheet-" + i;
      String activeClass = (i == 0) ? " active" : "";

      contentHtml
          .append("<div id=\"tab-")
          .append(tabId)
          .append("\" class=\"tab-content")
          .append(activeClass)
          .append("\">");

      int rowCount = 0;
      boolean hasData = false;

      contentHtml.append("<table>");

      for (Row row : sheet) {
        if (rowCount >= MAX_ROWS) {
          contentHtml
              .append("<tr><td colspan=\"")
              .append(MAX_COLS)
              .append("\"><em>... truncated at ")
              .append(MAX_ROWS)
              .append(" rows</em></td></tr>");
          break;
        }

        contentHtml.append("<tr>");

        int lastCellNum = row.getLastCellNum();
        boolean rowHasData = false;

        for (int colIndex = 0; colIndex < lastCellNum && colIndex < MAX_COLS; colIndex++) {
          Cell cell = row.getCell(colIndex);
          String cellValue = getCellValue(cell);
          if (!cellValue.isEmpty()) {
            rowHasData = true;
          }
          contentHtml.append("<td>").append(cellValue).append("</td>");
        }

        if (lastCellNum > MAX_COLS) {
          contentHtml.append("<td><em>... ").append(lastCellNum).append("+ columns</em></td>");
        }

        contentHtml.append("</tr>");

        if (rowHasData) {
          hasData = true;
        }
        rowCount++;
      }

      contentHtml.append("</table>");

      if (!hasData) {
        contentHtml.append("<p class=\"empty-cell\">This sheet is empty.</p>");
      }

      contentHtml.append("</div>");
    }
  }

  private String getCellValue(Cell cell) {
    if (cell == null) {
      return "<span class=\"empty-cell\">&nbsp;</span>";
    }

    CellType cellType = cell.getCellType();

    try {
      switch (cellType) {
        case STRING:
          return escapeHtml(cell.getStringCellValue());
        case NUMERIC:
          double numValue = cell.getNumericCellValue();
          if (numValue == Math.floor(numValue) && !Double.isInfinite(numValue)) {
            return String.valueOf((long) numValue);
          }
          return String.valueOf(numValue);
        case BOOLEAN:
          return String.valueOf(cell.getBooleanCellValue());
        case FORMULA:
          try {
            return escapeHtml(cell.getStringCellValue());
          } catch (Exception e) {
            try {
              return String.valueOf(cell.getNumericCellValue());
            } catch (Exception ex) {
              return escapeHtml(cell.getCellFormula());
            }
          }
        case BLANK:
          return "<span class=\"empty-cell\">&nbsp;</span>";
        case ERROR:
          return "<span class=\"empty-cell\">#ERROR</span>";
        default:
          return "<span class=\"empty-cell\">&nbsp;</span>";
      }
    } catch (Exception e) {
      return "<span class=\"empty-cell\">#ERROR</span>";
    }
  }

  private static String escapeHtml(String text) {
    if (text == null) {
      return "";
    }
    return text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("\n", "<br/>");
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
