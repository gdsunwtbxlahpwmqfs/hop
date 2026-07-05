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
package org.apache.hop.ui.hopgui.auth;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.hop.core.Const;
import org.apache.hop.core.logging.LogChannel;

/**
 * Logic layer: manages user credentials in a SQLite database stored under {@code
 * HOP_CONFIG_FOLDER}.
 *
 * <p>The database has a single {@code users} table:
 *
 * <pre>{@code
 * CREATE TABLE users (
 *   id            INTEGER PRIMARY KEY AUTOINCREMENT,
 *   username      TEXT    NOT NULL UNIQUE,
 *   password_hash TEXT    NOT NULL,           -- PasswordHasher stored format "salt:hash"
 *   display_name  TEXT,
 *   enabled       INTEGER NOT NULL DEFAULT 1, -- 1 = enabled, 0 = disabled
 *   created_at    TEXT    NOT NULL,
 *   updated_at    TEXT    NOT NULL
 * );
 * }</pre>
 *
 * <p>SQLite's WAL journal mode is enabled for better concurrency, and all writes are serialized via
 * an in-process lock to avoid SQLite write contention.
 */
public class UserStore {

  private static final String JDBC_PREFIX = "jdbc:sqlite:";

  private final Path dbFile;
  private final String jdbcUrl;
  private final ReentrantLock writeLock = new ReentrantLock();

  /** Loads the SQLite driver and initializes the schema. */
  public UserStore() {
    this.dbFile =
        Paths.get(Const.HOP_CONFIG_FOLDER).resolve(AuthConstants.DB_FILE_NAME).toAbsolutePath();
    this.jdbcUrl = JDBC_PREFIX + dbFile.toString();
    initDriver();
    initSchema();
    seedDefaultAdminIfEmpty();
  }

  /** Loads the SQLite JDBC driver. */
  private void initDriver() {
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(
          "SQLite JDBC driver not found on classpath. Did you add org.xerial:sqlite-jdbc?", e);
    }
  }

  /** Opens a new connection with sensible defaults. */
  private Connection openConnection() throws SQLException {
    Connection conn = DriverManager.getConnection(jdbcUrl);
    // Optimize for concurrency: WAL allows readers and a single writer simultaneously.
    try (Statement stmt = conn.createStatement()) {
      stmt.execute("PRAGMA journal_mode=WAL");
      stmt.execute("PRAGMA foreign_keys=ON");
      stmt.execute("PRAGMA busy_timeout=3000");
    }
    return conn;
  }

  /** Creates the {@code users} table if it does not exist. */
  private void initSchema() {
    try {
      Files.createDirectories(dbFile.getParent());
    } catch (Exception e) {
      LogChannel.UI.logError(
          "Failed to create Hop Web users DB directory: " + dbFile.getParent(), e);
    }
    String ddl =
        "CREATE TABLE IF NOT EXISTS users ("
            + "  id            INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "  username      TEXT    NOT NULL UNIQUE,"
            + "  password_hash TEXT    NOT NULL,"
            + "  display_name  TEXT,"
            + "  enabled       INTEGER NOT NULL DEFAULT 1,"
            + "  created_at    TEXT    NOT NULL,"
            + "  updated_at    TEXT    NOT NULL"
            + ")";
    try (Connection conn = openConnection();
        Statement stmt = conn.createStatement()) {
      stmt.execute(ddl);
      // Index for fast lookup by username (the UNIQUE constraint already creates one, but be
      // explicit for clarity).
      stmt.execute("CREATE INDEX IF NOT EXISTS idx_users_username ON users(username)");
    } catch (SQLException e) {
      LogChannel.UI.logError("Failed to initialize Hop Web users DB schema: " + dbFile, e);
    }
  }

  /**
   * Seeds a default admin user when no users exist (first start).
   *
   * <p>The plaintext default password is first run through SHA-256 (the same transform the browser
   * applies before submitting), so the stored credential matches what the client sends. The
   * plaintext password never reaches the database layer.
   */
  private void seedDefaultAdminIfEmpty() {
    if (hasUsers()) {
      return;
    }
    String initialPassword = AuthConstants.resolveInitialAdminPassword();
    // Match the client-side SHA-256 pre-hash so authentication works end-to-end.
    String clientHash = PasswordHasher.sha256Hex(initialPassword);
    addUser(AuthConstants.DEFAULT_ADMIN_USER, clientHash, "Administrator");
    LogChannel.UI.logBasic(
        "Hop Web: created default admin user '"
            + AuthConstants.DEFAULT_ADMIN_USER
            + "'. Please change the password immediately. DB: "
            + dbFile);
  }

  /**
   * Attempts to authenticate a username / password pair.
   *
   * <p>Disabled users ({@code enabled = 0}) cannot authenticate.
   *
   * @param username the username (case-sensitive)
   * @param password the raw password
   * @return {@code true} when credentials match and the user is enabled
   */
  public boolean authenticate(String username, String password) {
    if (username == null || password == null) {
      return false;
    }
    String sql = "SELECT password_hash, enabled FROM users WHERE username = ?";
    try (Connection conn = openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, username.trim());
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) {
          return false;
        }
        String storedHash = rs.getString("password_hash");
        int enabled = rs.getInt("enabled");
        if (enabled != 1) {
          return false;
        }
        return PasswordHasher.verify(password, storedHash);
      }
    } catch (SQLException e) {
      LogChannel.UI.logError("Hop Web: failed to authenticate user '" + username + "'", e);
      return false;
    }
  }

  /** Returns {@code true} when at least one user is configured. */
  public boolean hasUsers() {
    try (Connection conn = openConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users")) {
      return rs.next() && rs.getInt(1) > 0;
    } catch (SQLException e) {
      LogChannel.UI.logError("Hop Web: failed to count users", e);
      return false;
    }
  }

  /**
   * Adds a new user. Throws {@link SQLException} (wrapped in a runtime exception) if the username
   * already exists.
   *
   * @param username unique username
   * @param plainPassword raw password (will be hashed)
   * @param displayName optional display name (may be {@code null})
   */
  public void addUser(String username, String plainPassword, String displayName) {
    writeLock.lock();
    try {
      String sql =
          "INSERT INTO users (username, password_hash, display_name, enabled, created_at, updated_at) "
              + "VALUES (?, ?, ?, 1, ?, ?)";
      try (Connection conn = openConnection();
          PreparedStatement ps = conn.prepareStatement(sql)) {
        Timestamp now = Timestamp.from(Instant.now());
        ps.setString(1, username);
        ps.setString(2, PasswordHasher.hash(plainPassword));
        ps.setString(3, displayName);
        ps.setString(4, now.toString());
        ps.setString(5, now.toString());
        ps.executeUpdate();
      }
    } catch (SQLException e) {
      throw new RuntimeException("Failed to add user '" + username + "': " + e.getMessage(), e);
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * Convenience method: adds a user with no display name.
   *
   * @param username unique username
   * @param plainPassword raw password (will be hashed)
   */
  public void addUser(String username, String plainPassword) {
    addUser(username, plainPassword, null);
  }

  /**
   * Updates the password for an existing user.
   *
   * @param username the target username
   * @param newPlainPassword the new raw password (will be hashed)
   * @return {@code true} if a row was updated
   */
  public boolean updatePassword(String username, String newPlainPassword) {
    writeLock.lock();
    try {
      String sql = "UPDATE users SET password_hash = ?, updated_at = ? WHERE username = ?";
      try (Connection conn = openConnection();
          PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, PasswordHasher.hash(newPlainPassword));
        ps.setString(2, Timestamp.from(Instant.now()).toString());
        ps.setString(3, username);
        return ps.executeUpdate() > 0;
      }
    } catch (SQLException e) {
      LogChannel.UI.logError("Hop Web: failed to update password for '" + username + "'", e);
      return false;
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * Enables or disables a user. Disabled users cannot authenticate.
   *
   * @param username the target username
   * @param enabled {@code true} to enable, {@code false} to disable
   * @return {@code true} if a row was updated
   */
  public boolean setEnabled(String username, boolean enabled) {
    writeLock.lock();
    try {
      String sql = "UPDATE users SET enabled = ?, updated_at = ? WHERE username = ?";
      try (Connection conn = openConnection();
          PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, enabled ? 1 : 0);
        ps.setString(2, Timestamp.from(Instant.now()).toString());
        ps.setString(3, username);
        return ps.executeUpdate() > 0;
      }
    } catch (SQLException e) {
      LogChannel.UI.logError("Hop Web: failed to update enabled flag for '" + username + "'", e);
      return false;
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * Removes a user permanently.
   *
   * @param username the username to delete
   * @return {@code true} if a row was deleted
   */
  public boolean removeUser(String username) {
    writeLock.lock();
    try {
      try (Connection conn = openConnection();
          PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username = ?")) {
        ps.setString(1, username);
        return ps.executeUpdate() > 0;
      }
    } catch (SQLException e) {
      LogChannel.UI.logError("Hop Web: failed to remove user '" + username + "'", e);
      return false;
    } finally {
      writeLock.unlock();
    }
  }

  /** Returns the list of usernames (excluding disabled flag — call {@link #isEnabled} to check). */
  public List<String> listUsernames() {
    List<String> result = new ArrayList<>();
    try (Connection conn = openConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT username FROM users ORDER BY username")) {
      while (rs.next()) {
        result.add(rs.getString("username"));
      }
    } catch (SQLException e) {
      LogChannel.UI.logError("Hop Web: failed to list users", e);
    }
    return result;
  }

  /** Returns {@code true} if the user exists and is enabled. */
  public boolean isEnabled(String username) {
    try (Connection conn = openConnection();
        PreparedStatement ps =
            conn.prepareStatement("SELECT enabled FROM users WHERE username = ?")) {
      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() && rs.getInt("enabled") == 1;
      }
    } catch (SQLException e) {
      LogChannel.UI.logError("Hop Web: failed to check enabled flag for '" + username + "'", e);
      return false;
    }
  }

  /** Returns the absolute path of the SQLite database file (for diagnostics). */
  public Path getDbFile() {
    return dbFile;
  }
}
