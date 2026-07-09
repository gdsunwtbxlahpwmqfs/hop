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

import org.eclipse.swt.widgets.Shell;

/**
 * Abstract download service interface for downloading files from the server to the user's local
 * machine.
 *
 * <p>Implementations provide environment-specific download mechanisms:
 *
 * <ul>
 *   <li>{@link WebDownloadService} - Web (RAP) environment, uses Browser + HTTP download
 *   <li>{@link LocalDownloadService} - Desktop (RCP) environment, uses native FileDialog
 * </ul>
 */
public interface IDownloadService {

  /**
   * Downloads the specified file (or folder, as a zip) to the user's local machine.
   *
   * @param parentShell the parent SWT shell for any download dialog
   * @param filePath the absolute path of the file or folder to download
   * @param onComplete callback invoked after download completes (may be {@code null})
   * @return {@code true} if the download was initiated successfully, {@code false} if the user
   *     cancelled
   */
  boolean download(Shell parentShell, String filePath, Runnable onComplete);
}
