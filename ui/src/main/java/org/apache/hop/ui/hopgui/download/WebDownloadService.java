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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Web (RAP) download service implementation.
 *
 * <p>Creates a hidden {@link Browser} widget that navigates to the download URL served by {@code
 * FileDownloadServlet}. The servlet responds with {@code Content-Disposition: attachment}, which
 * triggers the browser's built-in download mechanism without showing any visible dialog.
 */
public class WebDownloadService implements IDownloadService {

  @Override
  public boolean download(Shell parentShell, String filePath, Runnable onComplete) {
    String encodedPath = URLEncoder.encode(filePath, StandardCharsets.UTF_8);
    String url = "/download?path=" + encodedPath;

    // Create a hidden browser widget to trigger the download.
    // The servlet responds with Content-Disposition: attachment, so the browser
    // downloads the file without showing any visible UI.
    Browser browser = new Browser(parentShell, SWT.NONE);
    browser.setVisible(false);

    browser.addProgressListener(
        new ProgressAdapter() {
          @Override
          public void completed(ProgressEvent event) {
            Display display = parentShell.getDisplay();
            display.timerExec(
                2000,
                () -> {
                  if (!browser.isDisposed()) {
                    browser.dispose();
                  }
                  if (onComplete != null) {
                    onComplete.run();
                  }
                });
          }
        });

    browser.setUrl(url);
    return true;
  }
}
