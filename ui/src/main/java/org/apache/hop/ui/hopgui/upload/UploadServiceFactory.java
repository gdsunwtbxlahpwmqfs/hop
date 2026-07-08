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

import org.apache.hop.ui.util.EnvironmentUtils;

/**
 * Factory that provides the appropriate {@link IUploadService} implementation based on the current
 * runtime environment.
 *
 * <p>Returns {@link WebUploadService} for Web (RAP) environments and {@link LocalUploadService} for
 * desktop (RCP) environments.
 */
public final class UploadServiceFactory {

  private static IUploadService webInstance;
  private static IUploadService localInstance;

  private UploadServiceFactory() {}

  /**
   * Returns the appropriate upload service for the current environment.
   *
   * @return {@link WebUploadService} in Web mode, {@link LocalUploadService} in desktop mode
   */
  public static synchronized IUploadService getInstance() {
    if (EnvironmentUtils.getInstance().isWeb()) {
      if (webInstance == null) {
        webInstance = new WebUploadService();
      }
      return webInstance;
    } else {
      if (localInstance == null) {
        localInstance = new LocalUploadService();
      }
      return localInstance;
    }
  }
}
