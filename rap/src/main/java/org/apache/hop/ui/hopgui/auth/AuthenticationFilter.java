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
package org.apache.hop.ui.hopgui.auth;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Enforces strict route control: any request to a protected path ({@code /ui}, {@code /ui-dark})
 * without an authenticated session attribute is redirected to the login page.
 *
 * <p>The original target URL is forwarded via the {@link AuthConstants#PARAM_REDIRECT} query
 * parameter so the login flow can return the user where they intended to go.
 */
public class AuthenticationFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
    // No initialization needed
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // Allow OPTIONS (CORS preflight / tus capability discovery) without auth
    if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
      chain.doFilter(request, response);
      return;
    }

    String contextPath = httpRequest.getContextPath();
    String requestUri = httpRequest.getRequestURI();

    // Strip the context path so we reason purely about the in-app path.
    String path = requestUri;
    if (contextPath != null && path.startsWith(contextPath)) {
      path = path.substring(contextPath.length());
    }

    // Only protect the Hop Web application entry points.
    if (!isProtectedPath(path)) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession session = httpRequest.getSession(false);
    if (session != null && session.getAttribute(AuthConstants.SESSION_ATTR_USER) != null) {
      chain.doFilter(request, response);
      return;
    }

    // Not authenticated: preserve the full target (path + query) as redirect target.
    String fullPath = path;
    String query = httpRequest.getQueryString();
    if (query != null && !query.isEmpty()) {
      fullPath += "?" + query;
    }
    String encoded = URLEncoder.encode(fullPath, StandardCharsets.UTF_8);
    String loginUrl =
        contextPath + AuthConstants.PATH_LOGIN + "?" + AuthConstants.PARAM_REDIRECT + "=" + encoded;

    // AJAX / fetch requests get a 401 so the client can react instead of following a redirect.
    String requestedWith = httpRequest.getHeader("X-Requested-With");
    if ("XMLHttpRequest".equalsIgnoreCase(requestedWith)) {
      httpResponse.setHeader("Location", loginUrl);
      httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    httpResponse.sendRedirect(loginUrl);
  }

  @Override
  public void destroy() {
    // No resources to release
  }

  /**
   * Returns {@code true} for the Hop Web application entry points that require authentication.
   *
   * <p>Note: {@code /upload} and {@code /download} are intentionally NOT protected here. These
   * dialogs are rendered inside an RAP Browser iframe which does not reliably share the JSESSIONID
   * cookie with the parent RAP session, so the filter would redirect it to the login page. The
   * {@code TusUploadServlet} and {@code FileDownloadServlet} themselves are safe: they only
   * write/read files at the path passed by the already-authenticated Hop GUI, and do not expose or
   * modify unrelated data.
   *
   * @param path the in-app request path (without context path)
   */
  private boolean isProtectedPath(String path) {
    if (path == null) {
      return false;
    }
    return path.equals("/ui")
        || path.equals("/ui-dark")
        || path.startsWith("/ui/")
        || path.startsWith("/ui-dark/");
  }
}
