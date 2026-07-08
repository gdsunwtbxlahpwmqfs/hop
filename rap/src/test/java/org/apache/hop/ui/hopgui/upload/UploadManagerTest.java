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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;
import org.apache.hop.ui.hopgui.upload.UploadSession.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class UploadManagerTest {

  @TempDir File tempDir;

  @Test
  void testCreateAndGetSession() {
    UploadManager mgr = UploadManager.getInstance();
    File tempFile = new File(tempDir, "test.tmp");
    UploadSession session =
        mgr.createSession("test-id", 1000L, "test.txt", tempDir.getAbsolutePath(), tempFile);

    assertNotNull(session);
    assertEquals("test-id", session.getId());
    assertEquals(1000L, session.getTotalLength());
    assertEquals(0L, session.getOffset());
    assertEquals(Status.CREATED, session.getStatus());

    UploadSession retrieved = mgr.getSession("test-id");
    assertNotNull(retrieved);
    assertEquals("test-id", retrieved.getId());
  }

  @Test
  void testUpdateOffset() {
    UploadManager mgr = UploadManager.getInstance();
    File tempFile = new File(tempDir, "offset.tmp");
    mgr.createSession("offset-id", 500L, "offset.txt", tempDir.getAbsolutePath(), tempFile);

    mgr.updateOffset("offset-id", 200L);
    UploadSession session = mgr.getSession("offset-id");
    assertEquals(200L, session.getOffset());

    mgr.updateOffset("offset-id", 500L);
    assertEquals(500L, session.getOffset());
  }

  @Test
  void testUpdateStatus() {
    UploadManager mgr = UploadManager.getInstance();
    File tempFile = new File(tempDir, "status.tmp");
    mgr.createSession("status-id", 500L, "status.txt", tempDir.getAbsolutePath(), tempFile);

    mgr.updateStatus("status-id", Status.UPLOADING);
    assertEquals(Status.UPLOADING, mgr.getSession("status-id").getStatus());

    mgr.updateStatus("status-id", Status.COMPLETED);
    assertEquals(Status.COMPLETED, mgr.getSession("status-id").getStatus());
  }

  @Test
  void testRemoveSession() throws IOException {
    UploadManager mgr = UploadManager.getInstance();
    File tempFile = new File(tempDir, "remove.tmp");
    tempFile.createNewFile();
    assert tempFile.exists();

    mgr.createSession("remove-id", 500L, "remove.txt", tempDir.getAbsolutePath(), tempFile);
    assertNotNull(mgr.getSession("remove-id"));

    mgr.removeSession("remove-id");
    assertNull(mgr.getSession("remove-id"));
    assert !tempFile.exists() : "Temp file should be deleted";
  }

  @Test
  void testAppendChunk() throws IOException {
    UploadManager mgr = UploadManager.getInstance();
    File tempFile = new File(tempDir, "chunk.tmp");
    mgr.createSession("chunk-id", 100L, "chunk.txt", tempDir.getAbsolutePath(), tempFile);

    byte[] data = "Hello, World!".getBytes();
    mgr.appendChunk("chunk-id", data, data.length);
    mgr.updateOffset("chunk-id", (long) data.length);

    UploadSession session = mgr.getSession("chunk-id");
    assertEquals(data.length, session.getOffset());
    assertEquals(tempFile.length(), data.length);
  }

  @Test
  void testGetNonExistentSession() {
    UploadManager mgr = UploadManager.getInstance();
    assertNull(mgr.getSession("non-existent"));
  }
}
