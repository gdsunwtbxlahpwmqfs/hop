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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility layer: salted SHA-256 password hashing and verification.
 *
 * <p>Hashes are stored as {@code base64(salt) + ":" + base64(hash)} so they are self-contained and
 * portable.
 */
public final class PasswordHasher {

  private static final String ALGORITHM = "SHA-256";
  private static final int SALT_LENGTH = 16;
  private static final int ITERATIONS = 10000;

  private PasswordHasher() {}

  /**
   * Computes the SHA-256 hash of a string and returns it as a lowercase hex string.
   *
   * <p>This is the <em>client hash</em>: the browser computes the same value before sending the
   * credential, so the plaintext password never traverses the network. The result is then fed into
   * {@link #hash(String)} / {@link #verify(String, String)} which apply their own salt + iterations
   * for database storage.
   *
   * @param input the raw input (e.g. the plaintext password)
   * @return 64-character lowercase hex SHA-256 digest
   */
  public static String sha256Hex(String input) {
    try {
      MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
      byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(bytes.length * 2);
      for (byte b : bytes) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 not available", e);
    }
  }

  /**
   * Hashes a plain-text password with a random salt.
   *
   * @param plain the raw password (or the client-side SHA-256 hex hash of the password)
   * @return a stored-format string {@code salt:hash} (both base64)
   */
  public static String hash(String plain) {
    byte[] salt = new byte[SALT_LENGTH];
    new SecureRandom().nextBytes(salt);
    byte[] hash = computeHash(plain, salt);
    Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
    return encoder.encodeToString(salt) + ":" + encoder.encodeToString(hash);
  }

  /**
   * Verifies a plain-text password against a stored hash.
   *
   * @param plain the raw password supplied by the user
   * @param stored the stored-format string ({@code salt:hash})
   * @return {@code true} when the password matches
   */
  public static boolean verify(String plain, String stored) {
    if (plain == null || stored == null) {
      return false;
    }
    int sep = stored.indexOf(':');
    if (sep <= 0 || sep >= stored.length() - 1) {
      return false;
    }
    try {
      Base64.Decoder decoder = Base64.getDecoder();
      byte[] salt = decoder.decode(stored.substring(0, sep));
      byte[] expected = decoder.decode(stored.substring(sep + 1));
      byte[] actual = computeHash(plain, salt);
      return MessageDigest.isEqual(expected, actual);
    } catch (IllegalArgumentException e) {
      return false; // malformed base64
    }
  }

  /**
   * Computes the salted, iterated hash.
   *
   * @param plain the raw password
   * @param salt the salt bytes
   * @return the resulting hash bytes
   */
  private static byte[] computeHash(String plain, byte[] salt) {
    try {
      MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
      digest.update(salt);
      byte[] result = digest.digest(plain.getBytes(StandardCharsets.UTF_8));
      // Iterate to slow down brute-force attacks
      for (int i = 0; i < ITERATIONS; i++) {
        digest.reset();
        result = digest.digest(result);
      }
      return result;
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 not available", e);
    }
  }
}
