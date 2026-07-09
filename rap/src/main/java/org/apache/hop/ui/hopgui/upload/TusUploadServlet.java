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
package org.apache.hop.ui.hopgui.upload;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.hopgui.auth.AuthConstants;
import org.apache.hop.ui.hopgui.upload.UploadSession.Status;

public class TusUploadServlet extends HttpServlet {

  private static final Class<?> PKG = TusUploadServlet.class; // i18n

  private static final Logger logger = Logger.getLogger(TusUploadServlet.class.getName());

  private static final String RESOURCE_PREFIX = "org/apache/hop/ui/hopgui/upload/";

  private static final String TUS_VERSION = "1.0.0";
  private static final String SESSION_ATTR_LAST_CHANGE = "hop.upload.lastChange";

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setHeader("Tus-Resumable", TUS_VERSION);

    String requestUri = req.getRequestURI();
    String contextPath = req.getContextPath();
    String path = requestUri.substring(contextPath.length());

    // Dispatch resource requests before method-based routing
    if (path.startsWith(AuthConstants.PATH_UPLOAD_RESOURCES)) {
      doGetResource(req, resp);
      return;
    }

    // HttpServlet does not natively support PATCH; route it manually
    if ("PATCH".equalsIgnoreCase(req.getMethod())) {
      doPatch(req, resp);
      return;
    }

    super.service(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String pathInfo = req.getPathInfo();

    // GET /upload/ or /upload → serve the upload page
    if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
      serveUploadPage(req, resp);
      return;
    }

    // GET /upload-resources/* → serve static resources (CSS, JS, etc.)
    if (pathInfo.startsWith("/upload-resources/")) {
      String fileName = pathInfo.substring("/upload-resources/".length());
      serveResource(fileName, resp);
      return;
    }

    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String uploadLengthHeader = req.getHeader("Upload-Length");
    if (uploadLengthHeader == null || uploadLengthHeader.isEmpty()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Upload-Length header");
      return;
    }

    long totalLength;
    try {
      totalLength = Long.parseLong(uploadLengthHeader);
    } catch (NumberFormatException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Upload-Length");
      return;
    }

    long maxSize = UploadConfig.getMaxSize();
    if (maxSize > 0 && totalLength > maxSize) {
      resp.sendError(
          HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,
          "File exceeds maximum upload size of " + maxSize + " bytes");
      return;
    }

    String metadataHeader = req.getHeader("Upload-Metadata");
    String filename = "unknown";
    String destPath = "";
    if (metadataHeader != null && !metadataHeader.isEmpty()) {
      for (String pair : metadataHeader.split(",")) {
        pair = pair.trim();
        int space = pair.indexOf(' ');
        String key = space > 0 ? pair.substring(0, space) : pair;
        String value = space > 0 ? pair.substring(space + 1).trim() : "";
        if ("filename".equals(key) && !value.isEmpty()) {
          filename = new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
        } else if ("destPath".equals(key) && !value.isEmpty()) {
          destPath = new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
        }
      }
    }

    // Sanitize filename
    filename = sanitizeFilename(filename);

    String id = UUID.randomUUID().toString();
    File tempDir = UploadConfig.getTempDir().toFile();
    if (!tempDir.exists() && !tempDir.mkdirs()) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot create temp directory");
      return;
    }
    File tempFile = new File(tempDir, id + ".tmp");

    UploadSession session =
        UploadManager.getInstance().createSession(id, totalLength, filename, destPath, tempFile);
    session.setStatus(Status.UPLOADING);

    resp.setStatus(HttpServletResponse.SC_CREATED);
    resp.setHeader("Location", req.getContextPath() + AuthConstants.PATH_UPLOAD + "/" + id);
  }

  @Override
  protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String id = extractId(req);
    if (id == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    UploadSession session = UploadManager.getInstance().getSession(id);
    if (session == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Upload session not found");
      return;
    }

    resp.setHeader("Upload-Offset", String.valueOf(session.getOffset()));
    resp.setHeader("Upload-Length", String.valueOf(session.getTotalLength()));
  }

  @Override
  protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String id = extractId(req);
    if (id == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    UploadSession session = UploadManager.getInstance().getSession(id);
    if (session == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Upload session not found");
      return;
    }

    String offsetHeader = req.getHeader("Upload-Offset");
    if (offsetHeader == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Upload-Offset header");
      return;
    }

    long expectedOffset;
    try {
      expectedOffset = Long.parseLong(offsetHeader);
    } catch (NumberFormatException e) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Upload-Offset");
      return;
    }

    long currentOffset = session.getOffset();
    if (expectedOffset != currentOffset) {
      resp.sendError(
          HttpServletResponse.SC_CONFLICT,
          "Offset mismatch: expected " + currentOffset + " but got " + expectedOffset);
      return;
    }

    byte[] buffer = new byte[8192];
    int totalRead = 0;
    try (InputStream in = req.getInputStream()) {
      int read;
      while ((read = in.read(buffer)) != -1) {
        UploadManager.getInstance().appendChunk(id, buffer, read);
        totalRead += read;
      }
    }

    long newOffset = currentOffset + totalRead;
    UploadManager.getInstance().updateOffset(id, newOffset);
    resp.setHeader("Upload-Offset", String.valueOf(newOffset));

    // Check completion
    LogChannel.UI.logBasic(
        "PATCH debug: id="
            + id
            + " newOffset="
            + newOffset
            + " totalLength="
            + session.getTotalLength()
            + " destPath="
            + session.getDestPath()
            + " filename="
            + session.getFilename());
    if (newOffset >= session.getTotalLength()) {
      try {
        completeUpload(session, resp, req);
      } catch (Exception e) {
        LogChannel.UI.logError("completeUpload failed: " + e.getMessage(), e);
        throw new IOException(e);
      }
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String id = extractId(req);
    if (id == null) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    UploadManager.getInstance().removeSession(id);
    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    resp.setContentLength(0);
    resp.setHeader("Tus-Version", TUS_VERSION);
    resp.setHeader("Tus-Extension", "creation,termination");
    long maxSize = UploadConfig.getMaxSize();
    if (maxSize > 0) {
      resp.setHeader("Tus-Max-Size", String.valueOf(maxSize));
    }
    resp.setHeader("Tus-Resumable", TUS_VERSION);
  }

  // -- Private helpers --

  private String extractId(HttpServletRequest req) {
    String pathInfo = req.getPathInfo();
    if (pathInfo == null || pathInfo.equals("/")) {
      return null;
    }
    // pathInfo = /{id}
    String id = pathInfo.substring(1);
    if (id.isEmpty() || id.contains("/")) {
      return null;
    }
    return id;
  }

  private void doGetResource(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();
    if (pathInfo == null || pathInfo.equals("/")) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    serveResource(pathInfo.substring(1), resp);
  }

  private void serveUploadPage(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("text/html;charset=UTF-8");
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    String html = readResource("upload.html");
    String resourcePrefix = req.getContextPath() + AuthConstants.PATH_UPLOAD_RESOURCES;
    html = html.replace("<!--RESOURCE_PREFIX-->", resourcePrefix);

    String dest = req.getParameter("dest");
    if (dest == null) {
      dest = "";
    }
    String chunkSize = String.valueOf(UploadConfig.getChunkSize());
    String maxSize = String.valueOf(UploadConfig.getMaxSize());

    // Build i18n JSON object from messages properties
    StringBuilder i18n = new StringBuilder();
    i18n.append("<script>window.i18n={");
    appendI18n(i18n, "pageTitle", "Upload.Page.Title");
    appendI18n(i18n, "pageHeading", "Upload.Page.Heading");
    appendI18n(i18n, "destDirLabel", "Upload.DestDir.Label");
    appendI18n(i18n, "strategyLabel", "Upload.Strategy.Label");
    appendI18n(i18n, "strategyRename", "Upload.Strategy.Rename");
    appendI18n(i18n, "strategyOverwrite", "Upload.Strategy.Overwrite");
    appendI18n(i18n, "dropAreaText", "Upload.DropArea.Text");
    appendI18n(i18n, "tableName", "Upload.Table.Name");
    appendI18n(i18n, "tableSize", "Upload.Table.Size");
    appendI18n(i18n, "tableProgress", "Upload.Table.Progress");
    appendI18n(i18n, "tableSpeed", "Upload.Table.Speed");
    appendI18n(i18n, "tableStatus", "Upload.Table.Status");
    appendI18n(i18n, "tableAction", "Upload.Table.Action");
    appendI18n(i18n, "empty", "Upload.Empty");
    appendI18n(i18n, "pauseAll", "Upload.PauseAll");
    appendI18n(i18n, "cancelAll", "Upload.CancelAll");
    appendI18n(i18n, "close", "Upload.Close");
    appendI18n(i18n, "statusUploading", "Upload.Status.Uploading");
    appendI18n(i18n, "statusPaused", "Upload.Status.Paused");
    appendI18n(i18n, "statusCompleted", "Upload.Status.Completed");
    appendI18n(i18n, "statusFailed", "Upload.Status.Failed");
    appendI18n(i18n, "statusCancelled", "Upload.Status.Cancelled");
    appendI18n(i18n, "statusWaiting", "Upload.Status.Waiting");
    appendI18n(i18n, "statusPause", "Upload.Status.Pause");
    appendI18n(i18n, "statusResume", "Upload.Status.Resume");
    appendI18n(i18n, "statusRetry", "Upload.Status.Retry");
    appendI18n(i18n, "remove", "Upload.Remove");
    appendI18n(i18n, "confirmClose", "Upload.Confirm.Close");
    appendI18n(i18n, "errorTooLarge", "Upload.Error.TooLarge");
    i18n.append("};</script>");
    html = html.replace("<!--I18N_CONFIG-->", i18n.toString());

    StringBuilder config = new StringBuilder();
    config.append("<script id=\"upload-config\" type=\"application/json\">{");
    config.append("\"contextPath\":\"").append(escapeJson(req.getContextPath())).append("\"");
    config.append(",\"dest\":\"").append(escapeJson(dest)).append("\"");
    config.append(",\"chunkSize\":").append(chunkSize);
    config.append(",\"maxSize\":").append(maxSize);
    config.append("}</script>");

    html = html.replace("<!--UPLOAD_CONFIG-->", config.toString());

    try (PrintWriter out = resp.getWriter()) {
      out.print(html);
    }
  }

  private void serveResource(String fileName, HttpServletResponse response) throws IOException {
    String contentType;
    if (fileName.endsWith(".css")) {
      contentType = "text/css;charset=UTF-8";
    } else if (fileName.endsWith(".js")) {
      contentType = "application/javascript;charset=UTF-8";
    } else if (fileName.endsWith(".svg")) {
      contentType = "image/svg+xml";
    } else if (fileName.endsWith(".png")) {
      contentType = "image/png";
    } else {
      contentType = "application/octet-stream";
    }
    response.setContentType(contentType);
    response.setHeader("Cache-Control", "no-cache, must-revalidate");

    String resourcePath = RESOURCE_PREFIX + fileName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
        OutputStream out = response.getOutputStream()) {
      if (in == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }
      in.transferTo(out);
    }
  }

  private String readResource(String name) {
    String path = RESOURCE_PREFIX + name;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line).append('\n');
      }
      return sb.toString();
    } catch (IOException e) {
      throw new RuntimeException("Failed to read upload resource: " + path, e);
    }
  }

  private String escapeJson(String value) {
    if (value == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder(value.length() + 8);
    for (int i = 0; i < value.length(); i++) {
      char c = value.charAt(i);
      switch (c) {
        case '"':
          sb.append("\\\"");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        default:
          if (c < 0x20) {
            sb.append(String.format("\\u%04x", (int) c));
          } else {
            sb.append(c);
          }
      }
    }
    return sb.toString();
  }

  private void completeUpload(
      UploadSession session, HttpServletResponse resp, HttpServletRequest req) throws IOException {
    File tempFile = session.getTempFile();
    String destPath = session.getDestPath();
    String filename = session.getFilename();

    // Resolve target path
    Path targetDir;
    if (destPath != null && !destPath.isEmpty()) {
      targetDir = Paths.get(destPath);
    } else {
      targetDir = Paths.get(".");
    }

    // Ensure directory exists
    Files.createDirectories(targetDir);

    // Handle naming conflict
    Path targetFile = targetDir.resolve(filename);
    targetFile = resolveConflict(targetFile);

    // Try multiple strategies to move the file to the target location.
    // macOS TCC may block Files.move() with "Operation not permitted" even when
    // the Unix permissions allow it. Files.copy() + Files.delete() is a workaround.
    boolean moved = false;
    Exception lastError = null;

    // Strategy 1: atomic move (fastest, but fails across filesystems)
    try {
      Files.move(tempFile.toPath(), targetFile, StandardCopyOption.ATOMIC_MOVE);
      moved = true;
    } catch (Exception e) {
      lastError = e;
    }

    // Strategy 2: regular move (handles cross-filesystem via copy+delete internally)
    if (!moved) {
      try {
        Files.move(tempFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        moved = true;
      } catch (Exception e) {
        lastError = e;
      }
    }

    // Strategy 3: explicit copy + delete (workaround for macOS TCC)
    if (!moved) {
      try {
        Files.copy(tempFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tempFile.toPath());
        moved = true;
      } catch (Exception e) {
        lastError = e;
      }
    }

    if (!moved) {
      String hint = "";
      if (lastError.getMessage() != null
          && lastError.getMessage().contains("Operation not permitted")) {
        hint =
            " (macOS TCC may be blocking access. Grant Full Disk Access to the Java runtime in System Settings > Privacy & Security.)";
      }
      throw new IOException("Failed to save uploaded file to " + targetFile + hint, lastError);
    }

    UploadManager.getInstance().updateStatus(session.getId(), Status.COMPLETED);
    UploadManager.getInstance().removeSession(session.getId());

    // Mark session attribute for Explorer refresh polling
    HttpSession httpSession = req.getSession(false);
    if (httpSession != null) {
      httpSession.setAttribute(SESSION_ATTR_LAST_CHANGE, System.currentTimeMillis());
    }

    LogChannel.UI.logBasic("Upload completed: " + targetFile.toAbsolutePath());
  }

  private Path resolveConflict(Path targetFile) {
    if (!Files.exists(targetFile)) {
      return targetFile;
    }
    String name = targetFile.getFileName().toString();
    String baseName;
    String extension;
    int dot = name.lastIndexOf('.');
    if (dot > 0) {
      baseName = name.substring(0, dot);
      extension = name.substring(dot);
    } else {
      baseName = name;
      extension = "";
    }
    Path parent = targetFile.getParent();
    for (int i = 1; i < 1000; i++) {
      Path candidate = parent.resolve(baseName + "_" + i + extension);
      if (!Files.exists(candidate)) {
        return candidate;
      }
    }
    return parent.resolve(baseName + "_" + System.currentTimeMillis() + extension);
  }

  static String sanitizeFilename(String name) {
    if (name == null || name.isEmpty()) {
      return "unknown";
    }
    String result = name.replaceAll("[/\\\\<>:\"|?*\0]", "_");
    if (result.isEmpty()) {
      return "unknown";
    }
    if (result.length() > 255) {
      result = result.substring(0, 255);
    }
    return result;
  }

  private void appendI18n(StringBuilder sb, String jsKey, String msgKey) {
    String val = BaseMessages.getString(PKG, msgKey);
    if (sb.length() > "<script>window.i18n={".length()) {
      sb.append(",");
    }
    sb.append("\"").append(jsKey).append("\":\"").append(escapeJson(val)).append("\"");
  }
}
