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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.apache.hop.ui.hopgui.upload.UploadSession.Status;

public class UploadManager {

  private static final Logger logger = Logger.getLogger(UploadManager.class.getName());

  private static final UploadManager INSTANCE = new UploadManager();

  private final ConcurrentHashMap<String, UploadSession> sessions = new ConcurrentHashMap<>();

  private final ScheduledExecutorService cleanupExecutor =
      Executors.newSingleThreadScheduledExecutor(
          r -> {
            Thread t = new Thread(r, "upload-sweeper");
            t.setDaemon(true);
            return t;
          });

  private UploadManager() {
    cleanupExecutor.scheduleAtFixedRate(this::sweepExpired, 5, 5, TimeUnit.MINUTES);
  }

  public static UploadManager getInstance() {
    return INSTANCE;
  }

  /** Shut down the background cleanup executor. Should be called on application shutdown. */
  public void shutdown() {
    cleanupExecutor.shutdownNow();
    logger.info("UploadManager cleanup executor shut down");
  }

  public UploadSession createSession(
      String id, long totalLength, String filename, String destPath, File tempFile) {
    UploadSession session = new UploadSession(id, totalLength, filename, destPath, tempFile);
    sessions.put(id, session);
    return session;
  }

  public UploadSession getSession(String id) {
    return sessions.get(id);
  }

  public void updateOffset(String id, long offset) {
    UploadSession session = sessions.get(id);
    if (session != null) {
      session.setOffset(offset);
    }
  }

  public void updateStatus(String id, Status status) {
    UploadSession session = sessions.get(id);
    if (session != null) {
      session.setStatus(status);
    }
  }

  public void removeSession(String id) {
    UploadSession session = sessions.remove(id);
    if (session != null && session.getTempFile() != null && session.getTempFile().exists()) {
      if (!session.getTempFile().delete()) {
        logger.warning("Failed to delete temp file: " + session.getTempFile().getAbsolutePath());
      }
    }
  }

  public void appendChunk(String id, byte[] data, int length) throws IOException {
    UploadSession session = sessions.get(id);
    if (session == null) {
      throw new IOException("No upload session found for id: " + id);
    }
    File tempFile = session.getTempFile();
    synchronized (tempFile.getAbsolutePath().intern()) {
      try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
          FileChannel channel = raf.getChannel()) {
        FileLock lock = null;
        try {
          lock = channel.tryLock();
        } catch (OverlappingFileLockException e) {
          throw new IOException("Concurrent write conflict on: " + tempFile.getAbsolutePath(), e);
        }
        try {
          raf.seek(raf.length());
          raf.write(data, 0, length);
        } finally {
          if (lock != null) {
            lock.release();
          }
        }
      }
    }
  }

  private void sweepExpired() {
    long expireHours = UploadConfig.getExpireHours();
    Instant cutoff = Instant.now().minus(Duration.ofHours(expireHours));
    for (String id : sessions.keySet()) {
      UploadSession session = sessions.get(id);
      if (session != null
          && session.getStatus() != Status.COMPLETED
          && session.getCreatedAt().isBefore(cutoff)) {
        logger.info("Sweeping expired upload session: " + id);
        removeSession(id);
      }
    }
  }
}
