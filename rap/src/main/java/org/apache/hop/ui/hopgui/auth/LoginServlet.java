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

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.i18n.BaseMessages;

/**
 * Handles three concerns in a single endpoint:
 *
 * <ul>
 *   <li><b>GET /login</b> – serves the login HTML page.
 *   <li><b>POST /login</b> – authenticates credentials (form-encoded or JSON) and creates the
 *       session, then either redirects (classic form post) or returns JSON (AJAX).
 *   <li><b>GET /login-resources/*</b> – serves the CSS / JS static assets for the login page.
 * </ul>
 */
public class LoginServlet extends HttpServlet {

  private static final Class<?> PKG = LoginServlet.class;

  private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

  private static final String RESOURCE_PREFIX = "org/apache/hop/ui/hopgui/auth/";

  private volatile UserStore userStore;

  /** Lazily initialises the user store so the config folder is ready at first use. */
  private UserStore getUserStore() {
    if (userStore == null) {
      synchronized (this) {
        if (userStore == null) {
          userStore = new UserStore();
        }
      }
    }
    return userStore;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String pathInfo = request.getPathInfo();

    // /login-resources/{file} -> serve static asset
    if (pathInfo != null && pathInfo.startsWith("/")) {
      serveResource(pathInfo.substring(1), response);
      return;
    }

    // /login (no path info) -> serve the HTML page
    serveLoginPage(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding(StandardCharsets.UTF_8.name());

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String remember = request.getParameter("remember");

    boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));

    boolean ok = getUserStore().authenticate(username, password);
    if (ok) {
      HttpSession session = request.getSession(true);
      session.setAttribute(AuthConstants.SESSION_ATTR_USER, username.trim());
      // When "remember me" is set, extend the session lifetime.
      if ("on".equalsIgnoreCase(remember) || "true".equalsIgnoreCase(remember)) {
        session.setMaxInactiveInterval(30 * 24 * 60 * 60); // 30 days
      }

      String redirect = request.getParameter(AuthConstants.PARAM_REDIRECT);
      String target = resolveTarget(request, redirect);

      if (isAjax) {
        sendJson(response, HttpServletResponse.SC_OK, "ok", target);
      } else {
        response.sendRedirect(target);
      }
      LogChannel.UI.logBasic("Hop Web login successful for user: " + username);
    } else {
      logger.warning("Hop Web login failed for user: " + username);
      String errorMsg = BaseMessages.getString(PKG, "LoginServlet.Alert.Error.InvalidCredentials");
      if (isAjax) {
        sendJson(response, HttpServletResponse.SC_UNAUTHORIZED, errorMsg, null);
      } else {
        String loginUrl = buildErrorRedirectUrl(request, errorMsg);
        response.sendRedirect(loginUrl);
      }
    }
  }

  /**
   * Resolves where to send the user after a successful login.
   *
   * @param request the current request
   * @param redirect the raw redirect parameter (already URL-decoded by the container)
   * @return a context-relative URL that is safe to redirect to
   */
  private String resolveTarget(HttpServletRequest request, String redirect) {
    String contextPath = request.getContextPath();
    if (redirect != null && !redirect.isEmpty()) {
      // Only allow in-app paths, never absolute/external URLs (open-redirect protection).
      if (redirect.startsWith("/") && !redirect.startsWith("//")) {
        return contextPath + redirect;
      }
    }
    return contextPath + "/ui";
  }

  /** Builds a redirect URL back to the login page carrying the error and original target. */
  private String buildErrorRedirectUrl(HttpServletRequest request, String error) {
    StringBuilder sb = new StringBuilder();
    sb.append(request.getContextPath()).append(AuthConstants.PATH_LOGIN).append("?error=1");
    String redirect = request.getParameter(AuthConstants.PARAM_REDIRECT);
    if (redirect != null && !redirect.isEmpty()) {
      sb.append("&").append(AuthConstants.PARAM_REDIRECT).append("=").append(redirect);
    }
    return sb.toString();
  }

  /** Sends a JSON response for AJAX login attempts. */
  private void sendJson(HttpServletResponse response, int status, String message, String redirect) {
    response.setStatus(status);
    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    try (PrintWriter out = response.getWriter()) {
      String redirectJson = redirect == null ? "null" : "\"" + escapeJson(redirect) + "\"";
      out.print(
          "{\"success\":"
              + (status == HttpServletResponse.SC_OK)
              + ",\"message\":\""
              + escapeJson(message)
              + "\",\"redirect\":"
              + redirectJson
              + "}");
    } catch (IOException e) {
      logger.warning("Failed to write JSON login response: " + e.getMessage());
    }
  }

  /** Serves the login HTML page, injecting the redirect target so JS can forward after login. */
  private void serveLoginPage(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");

    String html = readResource("login.html");
    String redirect = request.getParameter(AuthConstants.PARAM_REDIRECT);
    String errorFlag = request.getParameter("error");

    // Resolve the resource prefix so CSS/JS links work under any context path.
    String resourcePrefix = request.getContextPath() + AuthConstants.PATH_LOGIN_RESOURCES;
    html = html.replace("<!--RESOURCE_PREFIX-->", resourcePrefix);

    // Inject runtime values and i18n messages into the page via config script.
    StringBuilder config = new StringBuilder();
    config.append("<script id=\"login-config\" type=\"application/json\">{");
    config.append("\"contextPath\":\"").append(escapeJson(request.getContextPath())).append("\"");
    if (redirect != null && !redirect.isEmpty()) {
      config.append(",\"redirect\":\"").append(escapeJson(redirect)).append("\"");
    }
    if ("1".equals(errorFlag)) {
      config.append(",\"error\":true");
    }

    // Inject i18n messages
    config.append(",\"i18n\":{");
    appendI18n(config, "pageTitle", "LoginServlet.Page.Title");
    appendI18n(config, "pageSubtitle", "LoginServlet.Page.Subtitle");
    appendI18n(config, "accountLabel", "LoginServlet.Form.Account.Label");
    appendI18n(config, "accountPlaceholder", "LoginServlet.Form.Account.Placeholder");
    appendI18n(config, "passwordLabel", "LoginServlet.Form.Password.Label");
    appendI18n(config, "passwordPlaceholder", "LoginServlet.Form.Password.Placeholder");
    appendI18n(config, "passwordToggleTitle", "LoginServlet.Form.Password.Toggle.Title");
    appendI18n(config, "rememberLabel", "LoginServlet.Form.Remember.Label");
    appendI18n(config, "submitButton", "LoginServlet.Form.Submit.Button");
    appendI18n(config, "hintText", "LoginServlet.Form.Hint");
    appendI18n(config, "transitionText", "LoginServlet.Transition.Text");
    appendI18n(config, "errorInvalidCredentials", "LoginServlet.Alert.Error.InvalidCredentials");
    appendI18n(config, "errorRememberExpired", "LoginServlet.Alert.Error.RememberExpired");
    appendI18n(config, "errorPasswordEncrypt", "LoginServlet.Alert.Error.PasswordEncrypt");
    appendI18n(config, "errorLoginFailed", "LoginServlet.Alert.Error.LoginFailed");
    appendI18n(config, "valAccountEmpty", "LoginServlet.Validation.Account.Empty");
    appendI18n(config, "valAccountTooShort", "LoginServlet.Validation.Account.TooShort");
    appendI18n(config, "valAccountInvalid", "LoginServlet.Validation.Account.Invalid");
    appendI18n(config, "valPasswordEmpty", "LoginServlet.Validation.Password.Empty");
    appendI18n(config, "valPasswordTooShort", "LoginServlet.Validation.Password.TooShort");
    appendI18n(config, "valPasswordInvalid", "LoginServlet.Validation.Password.Invalid");
    config.append("}");

    config.append("}</script>");

    // Replace placeholder so the page knows its context path (important when deployed under a
    // non-root context).
    html = html.replace("<!--LOGIN_CONFIG-->", config.toString());
    try (PrintWriter out = response.getWriter()) {
      out.print(html);
    }
  }

  /** Appends an i18n key-value pair to the config JSON. */
  private void appendI18n(StringBuilder sb, String jsKey, String msgKey) {
    if (sb.charAt(sb.length() - 1) != '{') {
      sb.append(",");
    }
    String val = BaseMessages.getString(PKG, msgKey);
    sb.append("\"").append(jsKey).append("\":\"").append(escapeJson(val)).append("\"");
  }

  /**
   * Serves a static asset (CSS / JS) from the classpath.
   *
   * @param fileName the file name within the auth resource folder
   */
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
    // Login resources are small; use no-cache so users always get the latest JS/CSS
    // (avoids stale validation logic after updates).
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

  /** Reads a classpath resource as a UTF-8 string. */
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
      throw new RuntimeException("Failed to read login resource: " + path, e);
    }
  }

  /** Escapes a string for safe embedding inside a JSON string literal. */
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
}
