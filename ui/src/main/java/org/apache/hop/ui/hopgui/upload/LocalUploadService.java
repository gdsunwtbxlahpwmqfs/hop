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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.hop.i18n.BaseMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Desktop (RCP) upload service implementation.
 *
 * <p>Uses the native SWT {@link FileDialog} for file selection and {@link Files#copy} to copy
 * selected files into the target directory. This avoids the need for a running HTTP server or TUS
 * protocol on the desktop.
 */
public class LocalUploadService implements IUploadService {

  private static final Class<?> PKG = LocalUploadService.class;

  @Override
  public boolean upload(Shell parentShell, String destPath, Runnable onComplete) {
    FileDialog dialog = new FileDialog(parentShell, SWT.MULTI);
    dialog.setText(BaseMessages.getString(PKG, "LocalUploadService.DialogTitle"));
    String firstFile = dialog.open();
    if (firstFile == null) {
      return false;
    }

    String filterPath = dialog.getFilterPath();
    String[] fileNames = dialog.getFileNames();
    Path destDir = Paths.get(destPath);

    int successCount = 0;
    for (String fileName : fileNames) {
      Path source = Paths.get(filterPath, fileName);
      Path target = destDir.resolve(fileName);
      try {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        successCount++;
      } catch (IOException e) {
        // Continue with remaining files even if one fails
      }
    }

    if (onComplete != null) {
      onComplete.run();
    }
    return successCount > 0;
  }
}
