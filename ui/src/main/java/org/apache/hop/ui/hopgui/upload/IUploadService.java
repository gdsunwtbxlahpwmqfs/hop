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

import org.eclipse.swt.widgets.Shell;

/**
 * Abstract upload service interface for uploading files to a target directory.
 *
 * <p>Implementations provide environment-specific upload mechanisms:
 *
 * <ul>
 *   <li>{@link WebUploadService} - Web (RAP) environment, uses Browser + TUS protocol
 *   <li>{@link LocalUploadService} - Desktop (RCP) environment, uses native FileDialog
 * </ul>
 */
public interface IUploadService {

  /**
   * Opens an upload dialog allowing the user to select and upload files to the specified
   * destination directory.
   *
   * @param parentShell the parent SWT shell for the upload dialog
   * @param destPath the absolute path of the target directory
   * @param onComplete callback invoked after upload completes (may be {@code null})
   * @return {@code true} if files were uploaded successfully, {@code false} if the user cancelled
   */
  boolean upload(Shell parentShell, String destPath, Runnable onComplete);
}
