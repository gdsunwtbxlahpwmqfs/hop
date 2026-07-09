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
package org.apache.hop.ui.hopgui.download;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.hop.core.logging.LogChannel;

/**
 * Servlet that handles file downloads for the Hop Web (RAP) environment.
 *
 * <p>Accepts {@code GET /download?path=<url-encoded-path>} and streams the requested file (or zips
 * a directory) back to the browser with {@code Content-Disposition: attachment} so the browser
 * triggers a download.
 *
 * <p>Like {@code TusUploadServlet}, this servlet is intentionally not protected by the {@code
 * AuthenticationFilter} because the RAP Browser iframe does not reliably share the JSESSIONID
 * cookie. The download path is provided by the already-authenticated Hop GUI client.
 */
public class FileDownloadServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String pathParam = req.getParameter("path");
    if (pathParam == null || pathParam.isEmpty()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'path' parameter");
      return;
    }

    String filePath = URLDecoder.decode(pathParam, StandardCharsets.UTF_8);
    Path source = Paths.get(filePath);

    // Security: reject paths that don't exist
    if (!Files.exists(source)) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + filePath);
      return;
    }

    // Security: reject path traversal (..) in the encoded path
    if (filePath.contains("..")) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Path traversal is not allowed");
      return;
    }

    String fileName = source.getFileName().toString();

    if (Files.isDirectory(source)) {
      // Zip the directory and stream it
      String zipName = fileName + ".zip";
      resp.setContentType("application/zip");
      resp.setHeader(
          "Content-Disposition", "attachment; filename=\"" + encodeFilename(zipName) + "\"");

      try (OutputStream out = resp.getOutputStream();
          ZipOutputStream zos = new ZipOutputStream(out)) {
        Files.walk(source)
            .filter(p -> !Files.isDirectory(p))
            .forEach(
                p -> {
                  ZipEntry entry = new ZipEntry(source.relativize(p).toString().replace('\\', '/'));
                  try {
                    zos.putNextEntry(entry);
                    Files.copy(p, zos);
                    zos.closeEntry();
                  } catch (IOException e) {
                    // Skip files that can't be read
                  }
                });
      }
      LogChannel.UI.logBasic("Download (folder zip): " + source.toAbsolutePath());
    } else {
      // Stream a single file
      String contentType = Files.probeContentType(source);
      if (contentType == null) {
        contentType = "application/octet-stream";
      }
      resp.setContentType(contentType);
      long fileSize = Files.size(source);
      if (fileSize > 0) {
        resp.setContentLengthLong(fileSize);
      }
      resp.setHeader(
          "Content-Disposition", "attachment; filename=\"" + encodeFilename(fileName) + "\"");

      try (OutputStream out = resp.getOutputStream()) {
        Files.copy(source, out);
      }
      LogChannel.UI.logBasic("Download (file): " + source.toAbsolutePath());
    }
  }

  /**
   * Encodes a filename for use in the Content-Disposition header, falling back to RFC 5987 encoding
   * for non-ASCII characters.
   */
  private String encodeFilename(String name) {
    if (name.matches("[\\x00-\\x7F]+")) {
      // ASCII-safe — just escape quotes
      return name.replace("\"", "\\\"");
    }
    // RFC 5987 fallback for non-ASCII filenames
    String encoded = java.net.URLEncoder.encode(name, StandardCharsets.UTF_8).replace("+", "%20");
    return encoded;
  }
}
