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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TusUploadServletTest {

  @Mock HttpServletRequest req;
  @Mock HttpServletResponse resp;
  @Mock HttpSession session;

  private TusUploadServlet servlet;

  private StringWriter responseWriter;

  @BeforeEach
  void setUp() throws Exception {
    servlet = new TusUploadServlet();
    responseWriter = new StringWriter();
    lenient().when(resp.getWriter()).thenReturn(new PrintWriter(responseWriter));
  }

  @Test
  void testOptionsDeclaresCapabilities() throws Exception {
    servlet.doOptions(req, resp);

    verify(resp).setHeader("Tus-Version", "1.0.0");
    verify(resp).setHeader("Tus-Extension", "creation,termination");
    verify(resp).setHeader("Tus-Resumable", "1.0.0");
  }

  @Test
  void testPostCreatesSession() throws Exception {
    lenient().when(req.getContextPath()).thenReturn("");
    lenient().when(req.getRequestURI()).thenReturn("/upload/");
    lenient().when(req.getPathInfo()).thenReturn("/");
    lenient().when(req.getHeader("Upload-Length")).thenReturn("1000");
    String encodedFilename =
        Base64.getUrlEncoder().encodeToString("test.txt".getBytes(StandardCharsets.UTF_8));
    lenient()
        .when(req.getHeader("Upload-Metadata"))
        .thenReturn("filename " + encodedFilename + ", destPath " + encodedFilename);

    servlet.doPost(req, resp);

    verify(resp).setStatus(HttpServletResponse.SC_CREATED);
    ArgumentCaptor<String> locationCaptor = ArgumentCaptor.forClass(String.class);
    verify(resp).setHeader(org.mockito.ArgumentMatchers.eq("Location"), locationCaptor.capture());
    assertNotNull(locationCaptor.getValue());
    assertEquals("/upload/", locationCaptor.getValue().substring(0, 8));
  }

  @Test
  void testPostRejectsMissingLength() throws Exception {
    lenient().when(req.getHeader("Upload-Length")).thenReturn(null);

    servlet.doPost(req, resp);

    verify(resp).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Upload-Length header");
  }

  @Test
  void testHeadReturnsOffset() throws Exception {
    lenient().when(req.getContextPath()).thenReturn("");
    lenient().when(req.getRequestURI()).thenReturn("/upload/");
    lenient().when(req.getPathInfo()).thenReturn("/");
    lenient().when(req.getHeader("Upload-Length")).thenReturn("500");
    String encoded =
        Base64.getUrlEncoder().encodeToString("head.txt".getBytes(StandardCharsets.UTF_8));
    lenient().when(req.getHeader("Upload-Metadata")).thenReturn("filename " + encoded);

    servlet.doPost(req, resp);

    ArgumentCaptor<String> locationCaptor = ArgumentCaptor.forClass(String.class);
    verify(resp).setHeader(org.mockito.ArgumentMatchers.eq("Location"), locationCaptor.capture());
    String location = locationCaptor.getValue();
    String id = location.substring("/upload/".length());

    // HEAD request
    HttpServletRequest headReq = mock(HttpServletRequest.class);
    HttpServletResponse headResp = mock(HttpServletResponse.class);
    lenient().when(headReq.getContextPath()).thenReturn("");
    lenient().when(headReq.getRequestURI()).thenReturn("/upload/" + id);
    lenient().when(headReq.getPathInfo()).thenReturn("/" + id);

    servlet.doHead(headReq, headResp);

    verify(headResp).setHeader("Upload-Offset", "0");
    verify(headResp).setHeader("Upload-Length", "500");
  }

  @Test
  void testHeadReturns404ForUnknownId() throws Exception {
    lenient().when(req.getPathInfo()).thenReturn("/unknown-id");

    servlet.doHead(req, resp);

    verify(resp).sendError(HttpServletResponse.SC_NOT_FOUND, "Upload session not found");
  }

  @Test
  void testPatchRejectsOffsetMismatch() throws Exception {
    lenient().when(req.getContextPath()).thenReturn("");
    lenient().when(req.getRequestURI()).thenReturn("/upload/");
    lenient().when(req.getPathInfo()).thenReturn("/");
    lenient().when(req.getHeader("Upload-Length")).thenReturn("500");
    String encoded =
        Base64.getUrlEncoder().encodeToString("patch.txt".getBytes(StandardCharsets.UTF_8));
    lenient().when(req.getHeader("Upload-Metadata")).thenReturn("filename " + encoded);

    servlet.doPost(req, resp);

    ArgumentCaptor<String> locationCaptor = ArgumentCaptor.forClass(String.class);
    verify(resp).setHeader(org.mockito.ArgumentMatchers.eq("Location"), locationCaptor.capture());
    String id = locationCaptor.getValue().substring("/upload/".length());

    // PATCH with wrong offset
    HttpServletRequest patchReq = mock(HttpServletRequest.class);
    HttpServletResponse patchResp = mock(HttpServletResponse.class);
    lenient().when(patchReq.getContextPath()).thenReturn("");
    lenient().when(patchReq.getRequestURI()).thenReturn("/upload/" + id);
    lenient().when(patchReq.getPathInfo()).thenReturn("/" + id);
    lenient().when(patchReq.getHeader("Upload-Offset")).thenReturn("100");

    servlet.doPatch(patchReq, patchResp);

    verify(patchResp)
        .sendError(org.mockito.ArgumentMatchers.eq(HttpServletResponse.SC_CONFLICT), anyString());
  }

  @Test
  void testDeleteRemovesSession() throws Exception {
    lenient().when(req.getContextPath()).thenReturn("");
    lenient().when(req.getRequestURI()).thenReturn("/upload/");
    lenient().when(req.getPathInfo()).thenReturn("/");
    lenient().when(req.getHeader("Upload-Length")).thenReturn("500");
    String encoded =
        Base64.getUrlEncoder().encodeToString("delete.txt".getBytes(StandardCharsets.UTF_8));
    lenient().when(req.getHeader("Upload-Metadata")).thenReturn("filename " + encoded);

    servlet.doPost(req, resp);

    ArgumentCaptor<String> locationCaptor = ArgumentCaptor.forClass(String.class);
    verify(resp).setHeader(org.mockito.ArgumentMatchers.eq("Location"), locationCaptor.capture());
    String id = locationCaptor.getValue().substring("/upload/".length());

    // DELETE
    HttpServletRequest delReq = mock(HttpServletRequest.class);
    HttpServletResponse delResp = mock(HttpServletResponse.class);
    lenient().when(delReq.getContextPath()).thenReturn("");
    lenient().when(delReq.getRequestURI()).thenReturn("/upload/" + id);
    lenient().when(delReq.getPathInfo()).thenReturn("/" + id);

    servlet.doDelete(delReq, delResp);

    verify(delResp).setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

  @Test
  void testSanitizeFilename() {
    assertEquals("test.txt", TusUploadServlet.sanitizeFilename("test.txt"));
    assertEquals(".._safe_file", TusUploadServlet.sanitizeFilename("../safe/file"));
    assertEquals("a_b", TusUploadServlet.sanitizeFilename("a/b"));
    assertEquals("a_b", TusUploadServlet.sanitizeFilename("a\\b"));
    assertEquals("unknown", TusUploadServlet.sanitizeFilename(""));
    assertEquals("unknown", TusUploadServlet.sanitizeFilename(null));
    assertEquals("hello_world", TusUploadServlet.sanitizeFilename("hello:world"));
  }
}
