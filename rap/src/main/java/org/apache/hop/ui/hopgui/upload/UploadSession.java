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

import java.io.File;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadSession {

  public enum Status {
    CREATED,
    UPLOADING,
    COMPLETED,
    CANCELLED,
    FAILED
  }

  private final String id;
  private final long totalLength;
  private long offset;
  private final String filename;
  private final String destPath;
  private final File tempFile;
  private final Instant createdAt;
  private Status status;

  public UploadSession(
      String id, long totalLength, String filename, String destPath, File tempFile) {
    this.id = id;
    this.totalLength = totalLength;
    this.offset = 0;
    this.filename = filename;
    this.destPath = destPath;
    this.tempFile = tempFile;
    this.createdAt = Instant.now();
    this.status = Status.CREATED;
  }
}
