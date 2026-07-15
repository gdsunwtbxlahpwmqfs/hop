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

package org.apache.hop.rest.v1.resources.docs;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.hop.rest.v1.resources.BaseResource;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * REST API for serving plugin documentation from the local hop-assistant-manual. When the Hop GUI
 * (web mode) redirects a documentation URL, this endpoint resolves the URL to a local markdown file
 * and returns the content.
 */
@jakarta.ws.rs.Path("/docs")
public class DocsResource extends BaseResource {

  private static final Logger LOG = Logger.getLogger(DocsResource.class.getName());
  private static final String DOCS_BASE =
      System.getProperty("hop.docs.base", "docs/hop-assistant-manual");

  private final UrlMappingService mappingService = UrlMappingService.getInstance();

  private static final Parser MARKDOWN_PARSER;
  private static final HtmlRenderer MARKDOWN_RENDERER;

  static {
    java.util.List<Extension> extensions =
        java.util.List.of(TablesExtension.create());
    MARKDOWN_PARSER = Parser.builder().extensions(extensions).build();
    MARKDOWN_RENDERER = HtmlRenderer.builder().extensions(extensions).build();
  }

  // ---------------------------------------------------------------------------
  // Content-negotiated endpoints
  // ---------------------------------------------------------------------------

  /** Returns rendered HTML for browser display. */
  @GET
  @jakarta.ws.rs.Path("/{path:.+}")
  @Produces(MediaType.TEXT_HTML)
  public Response getDocumentHtml(@PathParam("path") String path) {
    DocResult result = resolveDocument(path);
    return switch (result.status) {
      case MATCHED -> Response.ok(renderHtmlPage(result.title, result.content, result.docUrl))
          .type(MediaType.TEXT_HTML_TYPE)
          .build();
      case UNMATCHED -> Response.ok(renderFallbackPage(result.message, result.fallbackUrl))
          .type(MediaType.TEXT_HTML_TYPE)
          .build();
      case NOT_FOUND, ERROR -> Response.status(Response.Status.NOT_FOUND)
          .entity(renderErrorPage(result.message))
          .type(MediaType.TEXT_HTML_TYPE)
          .build();
    };
  }

  /** Returns JSON for API consumers. */
  @GET
  @jakarta.ws.rs.Path("/{path:.+}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDocumentJson(@PathParam("path") String path) {
    DocResult result = resolveDocument(path);
    return switch (result.status) {
      case MATCHED -> {
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("status", "matched");
        response.put("title", result.title);
        response.put("content", result.content);
        response.put("format", "markdown");
        response.put("sourcePath", result.mdRelativePath);
        response.put("pluginId", result.pluginId != null ? result.pluginId : "");
        response.put("documentationUrl", result.docUrl);
        yield Response.ok(response).build();
      }
      case UNMATCHED -> Response.ok(
              Map.of(
                  "status", "unmatched",
                  "pluginId", result.pluginId != null ? result.pluginId : "",
                  "documentationUrl", result.docUrl,
                  "message", result.message,
                  "fallbackUrl", result.fallbackUrl))
          .build();
      case NOT_FOUND -> Response.status(Response.Status.NOT_FOUND)
          .entity(Map.of("status", "not_found", "documentationUrl", result.docUrl, "message",
              result.message))
          .build();
      case ERROR -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(Map.of("status", "error", "message", result.message))
          .build();
    };
  }

  // ---------------------------------------------------------------------------
  // Other endpoints (JSON only)
  // ---------------------------------------------------------------------------

  /** Returns all URL mappings. */
  @GET
  @jakarta.ws.rs.Path("/mappings")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMappings(@QueryParam("status") @DefaultValue("") String statusFilter) {
    try {
      var allMappings = mappingService.getAllMappings();
      if (statusFilter != null && !statusFilter.isBlank()) {
        allMappings = allMappings.stream().filter(m -> statusFilter.equals(m.getStatus())).toList();
      }
      return Response.ok(Map.of("mappings", allMappings, "count", allMappings.size())).build();
    } catch (Exception e) {
      return getServerError("Error getting mappings", e);
    }
  }

  /** Returns mapping statistics. */
  @GET
  @jakarta.ws.rs.Path("/stats")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStats() {
    try {
      return Response.ok(mappingService.getStats()).build();
    } catch (Exception e) {
      return getServerError("Error getting stats", e);
    }
  }

  /** Forces a reload of the mapping table. */
  @GET
  @jakarta.ws.rs.Path("/reload")
  @Produces(MediaType.APPLICATION_JSON)
  public Response reload() {
    try {
      mappingService.reload();
      return Response.ok(Map.of("reloaded", true, "stats", mappingService.getStats())).build();
    } catch (Exception e) {
      return getServerError("Error reloading mappings", e);
    }
  }

  // ---------------------------------------------------------------------------
  // Internal logic
  // ---------------------------------------------------------------------------

  private enum DocStatus {
    MATCHED,
    UNMATCHED,
    NOT_FOUND,
    ERROR
  }

  private static class DocResult {
    DocStatus status;
    String title;
    String content;
    String docUrl;
    String mdRelativePath;
    String pluginId;
    String message;
    String fallbackUrl;
  }

  /** Shared lookup logic used by both HTML and JSON endpoints. */
  private DocResult resolveDocument(String path) {
    DocResult r = new DocResult();
    r.docUrl = "/" + path;

    // Normalize hop.apache.org URLs
    if (r.docUrl.startsWith("/https://hop.apache.org/manual/")) {
      int slash = r.docUrl.indexOf('/', "/https://hop.apache.org/manual/".length());
      if (slash > 0) {
        r.docUrl = r.docUrl.substring(slash);
      }
    }

    try {
      UrlMapping mapping = mappingService.lookup(r.docUrl);

      if (mapping == null) {
        r.status = DocStatus.NOT_FOUND;
        r.message = "此文档路径未在映射表中注册";
        return r;
      }

      r.pluginId = mapping.getPluginId();

      if ("unmatched".equals(mapping.getStatus())) {
        r.status = DocStatus.UNMATCHED;
        r.message = "此插件暂无本地文档，已标记为待创建";
        r.fallbackUrl = "https://hop.apache.org/manual/next" + r.docUrl;
        return r;
      }

      String mdRelativePath = mapping.getMdRelativePath();
      if (mdRelativePath == null || mdRelativePath.isBlank()) {
        r.status = DocStatus.ERROR;
        r.message = "映射条目的 MD 路径为空";
        return r;
      }
      r.mdRelativePath = mdRelativePath;

      // 1) Try classpath (works in packaged WAR / Tomcat)
      String classpathPath = "/org/apache/hop/rest/docs/manual/" + mdRelativePath;
      String content;
      try (InputStream cpStream = getClass().getResourceAsStream(classpathPath)) {
        if (cpStream != null) {
          content = new String(cpStream.readAllBytes(), StandardCharsets.UTF_8);
        } else {
          // 2) Fallback: filesystem relative to docs base (dev mode)
          Path mdFile = Path.of(DOCS_BASE, mdRelativePath);
          if (!Files.exists(mdFile)) {
            LOG.warning("MD file not found: " + mdRelativePath);
            r.status = DocStatus.ERROR;
            r.message = "MD 文件不存在: " + mdRelativePath;
            return r;
          }
          content = Files.readString(mdFile, StandardCharsets.UTF_8);
        }
      }

      r.content = content;
      r.title = extractTitle(content);
      if (r.title == null) {
        r.title = r.pluginId;
      }
      r.status = DocStatus.MATCHED;
      return r;

    } catch (IOException e) {
      LOG.severe("Error reading documentation file: " + e.getMessage());
      r.status = DocStatus.ERROR;
      r.message = "读取文档文件失败: " + e.getMessage();
      return r;
    }
  }

  // ---------------------------------------------------------------------------
  // HTML rendering helpers
  // ---------------------------------------------------------------------------

  private String renderHtmlPage(String title, String markdownContent, String docUrl) {
    String htmlBody = MARKDOWN_RENDERER.render(MARKDOWN_PARSER.parse(markdownContent));
    return HTML_TEMPLATE
        .replace("{{TITLE}}", escapeHtml(title))
        .replace("{{CONTENT}}", htmlBody)
        .replace("{{DOC_URL}}", escapeHtml(docUrl));
  }

  private String renderFallbackPage(String message, String fallbackUrl) {
    String body =
        "<div class=\"hop-notice\">"
            + "<p>"
            + escapeHtml(message)
            + "</p>"
            + "<p><a href=\""
            + escapeHtml(fallbackUrl)
            + "\" target=\"_blank\">"
            + "查看在线文档"
            + " &#8599;</a></p>"
            + "</div>";
    return HTML_TEMPLATE
        .replace("{{TITLE}}", "文档未找到")
        .replace("{{CONTENT}}", body)
        .replace("{{DOC_URL}}", "");
  }

  private String renderErrorPage(String message) {
    String body =
        "<div class=\"hop-error\"><p>" + escapeHtml(message) + "</p></div>";
    return HTML_TEMPLATE
        .replace("{{TITLE}}", "错误")
        .replace("{{CONTENT}}", body)
        .replace("{{DOC_URL}}", "");
  }

  private static String escapeHtml(String text) {
    if (text == null) {
      return "";
    }
    return text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;");
  }

  private String extractTitle(String markdownContent) {
    if (markdownContent == null || markdownContent.isBlank()) {
      return null;
    }
    for (String line : markdownContent.split("\n")) {
      String trimmed = line.trim();
      if (trimmed.startsWith("# ")) {
        return trimmed.substring(2).trim();
      }
    }
    return null;
  }

  private static final String CSS_STYLES =
      ":root{--hop-primary:#3a87c8;--hop-bg:#f5f7fa;--hop-text:#333;--hop-border:#dde3ea}"
          + "*{box-sizing:border-box;margin:0;padding:0}"
          + "body{font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,'Helvetica Neue',Arial,sans-serif;"
          + "line-height:1.7;color:var(--hop-text);background:var(--hop-bg)}"
          + ".hop-header{background:#fff;border-bottom:3px solid var(--hop-primary);"
          + "box-shadow:0 1px 4px rgba(0,0,0,.08);position:sticky;top:0;z-index:10}"
          + ".hop-header-inner{max-width:960px;margin:0 auto;padding:14px 24px;"
          + "display:flex;align-items:center;gap:8px}"
          + ".hop-logo{font-weight:700;font-size:18px;color:var(--hop-primary)}"
          + ".hop-header-sep{color:var(--hop-border)}"
          + ".hop-header-title{font-size:15px;color:#666}"
          + ".hop-content{max-width:960px;margin:0 auto;padding:32px 24px;background:#fff;"
          + "min-height:calc(100vh - 120px);box-shadow:0 0 2px rgba(0,0,0,.04)}"
          + ".hop-content h1{font-size:28px;margin-bottom:16px;color:var(--hop-primary);"
          + "border-bottom:2px solid var(--hop-border);padding-bottom:10px}"
          + ".hop-content h2{font-size:22px;margin-top:32px;margin-bottom:12px;color:#1a1a1a}"
          + ".hop-content h3{font-size:18px;margin-top:24px;margin-bottom:8px}"
          + ".hop-content p{margin-bottom:14px}"
          + ".hop-content ul,.hop-content ol{margin:0 0 14px 24px}"
          + ".hop-content li{margin-bottom:6px}"
          + ".hop-content code{font-family:'SF Mono',Menlo,Consolas,monospace;"
          + "background:#eef2f6;padding:2px 6px;border-radius:3px;font-size:13px}"
          + ".hop-content pre{background:#1e2a3a;color:#e0e6ed;padding:16px;border-radius:8px;"
          + "overflow-x:auto;margin-bottom:16px}"
          + ".hop-content pre code{background:none;padding:0;color:inherit;font-size:13px}"
          + ".hop-content table{border-collapse:collapse;width:100%;margin-bottom:16px}"
          + ".hop-content th,.hop-content td{border:1px solid var(--hop-border);"
          + "padding:8px 12px;text-align:left}"
          + ".hop-content th{background:var(--hop-bg);font-weight:600}"
          + ".hop-content img{max-width:100%;height:auto}"
          + ".hop-content blockquote{border-left:4px solid var(--hop-primary);"
          + "margin:0 0 16px;padding:8px 16px;background:#f0f5fb;color:#555}"
          + ".hop-content a{color:var(--hop-primary);text-decoration:none}"
          + ".hop-content a:hover{text-decoration:underline}"
          + ".hop-notice{background:#fff8e1;border:1px solid #ffe082;border-radius:8px;"
          + "padding:20px 24px;text-align:center;margin:40px auto;max-width:480px}"
          + ".hop-notice a{display:inline-block;margin-top:12px;font-weight:600}"
          + ".hop-error{background:#fdecea;border:1px solid #f5c6cb;border-radius:8px;"
          + "padding:20px 24px;text-align:center;margin:40px auto;max-width:480px}"
          + ".hop-footer{max-width:960px;margin:0 auto;padding:16px 24px;"
          + "text-align:center;color:#999;font-size:13px}";

  private static final String HTML_TEMPLATE =
      "<!DOCTYPE html>\n"
          + "<html lang=\"zh-CN\">\n"
          + "<head>\n"
          + "<meta charset=\"UTF-8\">\n"
          + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
          + "<title>{{TITLE}} - Apache Hop 文档</title>\n"
          + "<style>\n"
          + CSS_STYLES
          + "\n</style>\n"
          + "</head>\n"
          + "<body>\n"
          + "<header class=\"hop-header\">\n"
          + "  <div class=\"hop-header-inner\">\n"
          + "    <span class=\"hop-logo\">Apache Hop</span>\n"
          + "    <span class=\"hop-header-sep\">|</span>\n"
          + "    <span class=\"hop-header-title\">{{TITLE}}</span>\n"
          + "  </div>\n"
          + "</header>\n"
          + "<main class=\"hop-content\">\n"
          + "{{CONTENT}}\n"
          + "</main>\n"
          + "<footer class=\"hop-footer\">\n"
          + "  <span>Apache Hop Documentation</span>\n"
          + "</footer>\n"
          + "</body>\n"
          + "</html>\n";
}
