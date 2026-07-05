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

/** Shared constants for the Hop Web authentication subsystem. */
public final class AuthConstants {

  private AuthConstants() {}

  /** HTTP session attribute storing the authenticated username. */
  public static final String SESSION_ATTR_USER = "hop.web.user";

  /** Query parameter used to carry the original target URL through the login redirect. */
  public static final String PARAM_REDIRECT = "redirect";

  /** SQLite database file name (stored under {@code HOP_CONFIG_FOLDER}). */
  public static final String DB_FILE_NAME = "hop-web-users.db";

  /** Default admin username created on first start when no users exist. */
  public static final String DEFAULT_ADMIN_USER = "admin";

  /**
   * Default admin password created on first start. Can be overridden via the {@code
   * hop.web.admin.password} system property or {@code HOP_WEB_ADMIN_PASSWORD} environment variable
   * for production deployments. Change immediately after first login.
   */
  public static final String DEFAULT_ADMIN_PASSWORD_FALLBACK = "admin@2026";

  /** System property key for overriding the default admin password on first start. */
  public static final String SYSPROP_ADMIN_PASSWORD = "hop.web.admin.password";

  /** Environment variable for overriding the default admin password on first start. */
  public static final String ENV_ADMIN_PASSWORD = "HOP_WEB_ADMIN_PASSWORD";

  /** URL paths for login and logout endpoints. */
  public static final String PATH_LOGIN = "/login";

  public static final String PATH_LOGOUT = "/logout";

  /** Path prefix for static login resources (CSS/JS). */
  public static final String PATH_LOGIN_RESOURCES = "/login-resources";

  /**
   * Resolves the initial admin password from system property / environment variable, falling back
   * to the hardcoded default. This is only used to seed the database on first start.
   */
  public static String resolveInitialAdminPassword() {
    String fromSysProp = System.getProperty(SYSPROP_ADMIN_PASSWORD);
    if (fromSysProp != null && !fromSysProp.isEmpty()) {
      return fromSysProp;
    }
    String fromEnv = System.getenv(ENV_ADMIN_PASSWORD);
    if (fromEnv != null && !fromEnv.isEmpty()) {
      return fromEnv;
    }
    return DEFAULT_ADMIN_PASSWORD_FALLBACK;
  }
}
