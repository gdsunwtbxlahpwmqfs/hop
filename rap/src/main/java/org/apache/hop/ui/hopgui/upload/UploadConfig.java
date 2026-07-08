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

import java.nio.file.Path;

public final class UploadConfig {

  private UploadConfig() {}

  public static final String SYSPROP_MAX_SIZE = "HOP_WEB_UPLOAD_MAX_SIZE";
  public static final String ENV_MAX_SIZE = "HOP_WEB_UPLOAD_MAX_SIZE";
  public static final String SYSPROP_CHUNK_SIZE = "HOP_WEB_UPLOAD_CHUNK_SIZE";
  public static final String ENV_CHUNK_SIZE = "HOP_WEB_UPLOAD_CHUNK_SIZE";
  public static final String SYSPROP_TEMP_DIR = "HOP_WEB_UPLOAD_TEMP_DIR";
  public static final String ENV_TEMP_DIR = "HOP_WEB_UPLOAD_TEMP_DIR";
  public static final String SYSPROP_EXPIRE_HOURS = "HOP_WEB_UPLOAD_EXPIRE_HOURS";
  public static final String ENV_EXPIRE_HOURS = "HOP_WEB_UPLOAD_EXPIRE_HOURS";

  private static final long DEFAULT_MAX_SIZE = 0L;
  private static final long DEFAULT_CHUNK_SIZE = 5L * 1024 * 1024;
  private static final long DEFAULT_EXPIRE_HOURS = 24L;

  public static long getMaxSize() {
    return resolveLong(SYSPROP_MAX_SIZE, ENV_MAX_SIZE, DEFAULT_MAX_SIZE);
  }

  public static long getChunkSize() {
    return resolveLong(SYSPROP_CHUNK_SIZE, ENV_CHUNK_SIZE, DEFAULT_CHUNK_SIZE);
  }

  public static Path getTempDir() {
    String val = System.getProperty(SYSPROP_TEMP_DIR);
    if (val == null || val.isEmpty()) {
      val = System.getenv(ENV_TEMP_DIR);
    }
    if (val == null || val.isEmpty()) {
      val = System.getProperty("java.io.tmpdir") + "/uploads-tmp";
    }
    return Path.of(val);
  }

  public static long getExpireHours() {
    return resolveLong(SYSPROP_EXPIRE_HOURS, ENV_EXPIRE_HOURS, DEFAULT_EXPIRE_HOURS);
  }

  private static long resolveLong(String sysProp, String envVar, long defaultValue) {
    String val = System.getProperty(sysProp);
    if (val == null || val.isEmpty()) {
      val = System.getenv(envVar);
    }
    if (val != null && !val.isEmpty()) {
      try {
        return Long.parseLong(val);
      } catch (NumberFormatException e) {
        // fall through
      }
    }
    return defaultValue;
  }
}
