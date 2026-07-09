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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.hop.i18n.BaseMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Desktop (RCP) download service implementation.
 *
 * <p>Uses the native SWT {@link FileDialog} (in SAVE mode) for choosing the destination, then
 * copies the selected file. If the selected path is a directory, it is zipped first.
 */
public class LocalDownloadService implements IDownloadService {

  private static final Class<?> PKG = LocalDownloadService.class;

  @Override
  public boolean download(Shell parentShell, String filePath, Runnable onComplete) {
    Path source = Paths.get(filePath);
    if (!Files.exists(source)) {
      return false;
    }

    FileDialog dialog = new FileDialog(parentShell, SWT.SAVE);
    dialog.setText(BaseMessages.getString(PKG, "LocalDownloadService.DialogTitle"));

    String fileName;
    if (Files.isDirectory(source)) {
      fileName = source.getFileName().toString() + ".zip";
      dialog.setFilterExtensions(new String[] {"*.zip"});
    } else {
      fileName = source.getFileName().toString();
      dialog.setFilterExtensions(new String[] {"*.*"});
    }
    dialog.setFileName(fileName);
    dialog.setOverwrite(true);

    String targetPath = dialog.open();
    if (targetPath == null) {
      return false;
    }

    Path target = Paths.get(targetPath);
    try {
      if (Files.isDirectory(source)) {
        zipDirectory(source, target);
      } else {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      // Continue — the error is reflected by the file not being present
    }

    if (onComplete != null) {
      onComplete.run();
    }
    return true;
  }

  private void zipDirectory(Path sourceDir, Path zipFile) throws IOException {
    try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
      Files.walk(sourceDir)
          .filter(path -> !Files.isDirectory(path))
          .forEach(
              path -> {
                ZipEntry entry =
                    new ZipEntry(sourceDir.relativize(path).toString().replace('\\', '/'));
                try {
                  zos.putNextEntry(entry);
                  Files.copy(path, zos);
                  zos.closeEntry();
                } catch (IOException e) {
                  // Skip files that can't be read
                }
              });
    }
  }
}
