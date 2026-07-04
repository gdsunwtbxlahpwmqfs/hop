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

package org.apache.hop.ui.hopgui.terminal;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import com.pty4j.WinSize;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.hop.core.Const;
import org.apache.hop.core.logging.LogChannel;

public class PtyWebSocketEndpoint {

  private static final LogChannel log = new LogChannel("PtyWS");
  private static final Map<String, PtySession> sessions = new ConcurrentHashMap<>();
  private static final String RESIZE_PREFIX = "\u0000RESIZE:";
  private static final ExecutorService readerPool =
      Executors.newCachedThreadPool(
          r -> {
            Thread t = new Thread(r, "pty-reader");
            t.setDaemon(true);
            return t;
          });

  @OnOpen
  public void onOpen(Session session) {
    String ptyId = session.getPathParameters().get("ptyId");
    if (ptyId == null || ptyId.isEmpty()) {
      try {
        session.close(
            new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Missing or empty ptyId"));
      } catch (IOException e) {
        log.logError("Failed to close session with invalid ptyId", e);
      }
      return;
    }
    try {
      String shellPath = getShellFromQuery(session);
      String workingDirectory = getWorkingDirFromQuery(session);

      String[] command = buildShellCommand(shellPath);
      Map<String, String> env = new HashMap<>(System.getenv());
      env.put("TERM", "xterm-256color");

      PtyProcessBuilder builder =
          new PtyProcessBuilder()
              .setCommand(command)
              .setDirectory(workingDirectory)
              .setEnvironment(env)
              .setInitialColumns(80)
              .setInitialRows(24);

      PtyProcess process = builder.start();
      PtySession ptySession = new PtySession(ptyId, session, process);
      sessions.put(ptyId, ptySession);

      // Start reading PTY stdout in background
      readerPool.submit(ptySession.new Reader());

      log.logBasic("Terminal PTY started: " + ptyId + " shell=" + shellPath);
    } catch (Exception e) {
      log.logError("Error starting PTY for " + ptyId, e);
      try {
        session.close();
      } catch (Exception ignored) {
      }
    }
  }

  @OnMessage
  public void onMessage(String message, Session session) {
    String ptyId = session.getPathParameters().get("ptyId");
    PtySession ptySession = sessions.get(ptyId);
    if (ptySession == null) {
      log.logDebug("No PTY session found for id: " + ptyId);
      return;
    }

    // Check for resize command
    if (message.startsWith(RESIZE_PREFIX)) {
      try {
        String[] parts = message.substring(RESIZE_PREFIX.length()).split(",");
        int cols = Integer.parseInt(parts[0].trim());
        int rows = Integer.parseInt(parts[1].trim());
        ptySession.process.setWinSize(new WinSize(cols, rows));
        log.logDebug("PTY resized to " + cols + "x" + rows + " for " + ptyId);
      } catch (Exception e) {
        log.logDebug("Failed to parse resize command: " + message, e);
      }
      return;
    }

    try {
      OutputStream os = ptySession.process.getOutputStream();
      os.write(message.getBytes(StandardCharsets.UTF_8));
      os.flush();
    } catch (IOException e) {
      log.logError("Error writing to PTY " + ptyId, e);
    }
  }

  @OnMessage(maxMessageSize = 65536)
  public void onBinary(ByteBuffer data, Session session) {
    String ptyId = session.getPathParameters().get("ptyId");
    PtySession ptySession = sessions.get(ptyId);
    if (ptySession == null) {
      log.logDebug("No PTY session found for id: " + ptyId);
      return;
    }
    try {
      OutputStream os = ptySession.process.getOutputStream();
      byte[] bytes = new byte[data.remaining()];
      data.get(bytes);
      os.write(bytes);
      os.flush();
    } catch (IOException e) {
      log.logError("Error writing binary to PTY " + ptyId, e);
    }
  }

  @OnClose
  public void onClose(Session session) {
    String ptyId = session.getPathParameters().get("ptyId");
    cleanup(ptyId);
  }

  @OnError
  public void onError(Session session, Throwable error) {
    String ptyId = session.getPathParameters().get("ptyId");
    log.logError("WebSocket error for PTY " + ptyId, error);
    cleanup(ptyId);
  }

  private static void cleanup(String ptyId) {
    PtySession ptySession = sessions.remove(ptyId);
    if (ptySession != null) {
      try {
        ptySession.process.destroy();
        log.logBasic("Terminal PTY destroyed: " + ptyId);
      } catch (Exception e) {
        log.logError("Error destroying PTY " + ptyId, e);
      }
    }
  }

  private static String getShellFromQuery(Session session) {
    String shell = getQueryParam(session, "shell");
    return shell != null ? shell : TerminalShellDetector.detectDefaultShell();
  }

  private static String getWorkingDirFromQuery(Session session) {
    String dir = getQueryParam(session, "cwd");
    return dir != null ? dir : System.getProperty("user.home");
  }

  private static String getQueryParam(Session session, String key) {
    String qs = session.getQueryString();
    if (qs == null) {
      return null;
    }
    for (String param : qs.split("&")) {
      String[] parts = param.split("=", 2);
      if (parts.length == 2 && parts[0].equals(key)) {
        try {
          return URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
        } catch (Exception e) {
          log.logDebug("Failed to decode query param: " + key, e);
          return parts[1];
        }
      }
    }
    return null;
  }

  private static String[] buildShellCommand(String shellPath) {
    String shell = shellPath != null ? shellPath : TerminalShellDetector.detectDefaultShell();
    if (Const.isWindows()) {
      if (shell.toLowerCase().contains("powershell")) {
        return new String[] {shell, "-NoLogo"};
      }
      return new String[] {shell};
    }
    return new String[] {shell, "-i", "-l"};
  }

  private static class PtySession {
    final String id;
    final Session session;
    final PtyProcess process;

    PtySession(String id, Session session, PtyProcess process) {
      this.id = id;
      this.session = session;
      this.process = process;
    }

    class Reader implements Runnable {
      @Override
      public void run() {
        try (InputStream is = process.getInputStream()) {
          byte[] buf = new byte[8192];
          int len;
          while ((len = is.read(buf)) != -1) {
            if (!session.isOpen()) {
              break;
            }
            byte[] chunk = new byte[len];
            System.arraycopy(buf, 0, chunk, 0, len);
            if (session.isOpen()) {
              session.getBasicRemote().sendBinary(ByteBuffer.wrap(chunk));
            }
          }
        } catch (IOException e) {
          if (!"Stream closed".equals(e.getMessage())) {
            log.logDebug("PTY reader stopped for " + id + ": " + e.getMessage());
          }
        } finally {
          try {
            if (session.isOpen()) {
              session.close();
            }
          } catch (Exception ignored) {
          }
          cleanup(id);
        }
      }
    }
  }
}
