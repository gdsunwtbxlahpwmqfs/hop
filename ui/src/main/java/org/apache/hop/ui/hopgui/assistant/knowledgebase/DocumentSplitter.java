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

package org.apache.hop.ui.hopgui.assistant.knowledgebase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Splits Markdown documents into chunks suitable for embedding. The splitter first segments by
 * headings ({@code ##}, {@code ###}), then splits over-sized segments by character count with
 * overlap, preserving context.
 */
public class DocumentSplitter {

  private final int chunkSize;
  private final int chunkOverlap;

  public DocumentSplitter(int chunkSize, int chunkOverlap) {
    this.chunkSize = chunkSize;
    this.chunkOverlap = chunkOverlap;
  }

  /**
   * Splits a single Markdown document into chunks.
   *
   * @param filePath the relative file path (used for metadata and id generation)
   * @param content the full Markdown text
   * @return a list of chunks
   */
  public List<KnowledgeChunk> split(String filePath, String content) {
    List<KnowledgeChunk> chunks = new ArrayList<>();
    Map<String, String> fileMetadata = extractMetadata(filePath);

    // Step 1: Split by Markdown headings
    List<Section> sections = splitByHeadings(content);

    // Step 2: Split over-sized sections by character count with overlap
    int chunkIndex = 0;
    for (Section section : sections) {
      List<String> textChunks = splitBySize(section.text(), chunkSize, chunkOverlap);
      for (String textChunk : textChunks) {
        String trimmed = textChunk.trim();
        if (trimmed.isEmpty()) {
          continue;
        }
        String id = generateId(filePath, chunkIndex);
        Map<String, String> metadata = new LinkedHashMap<>(fileMetadata);
        metadata.put("section", section.heading());
        chunks.add(
            new KnowledgeChunk(id, filePath, section.heading(), chunkIndex, trimmed, metadata));
        chunkIndex++;
      }
    }

    return chunks;
  }

  /** Splits text into sections based on Markdown headings (## and ###). */
  private List<Section> splitByHeadings(String content) {
    List<Section> sections = new ArrayList<>();
    String[] lines = content.split("\n", -1);
    StringBuilder currentText = new StringBuilder();
    String currentHeading = "";

    for (String line : lines) {
      String trimmed = line.trim();
      if (trimmed.startsWith("## ") || trimmed.startsWith("### ")) {
        // Flush previous section
        if (currentText.length() > 0) {
          sections.add(new Section(currentHeading, currentHeading + "\n\n" + currentText));
        }
        currentHeading = trimmed.replaceAll("^#+\\s*", "");
        currentText = new StringBuilder();
      } else {
        currentText.append(line).append("\n");
      }
    }
    // Flush last section
    if (currentText.length() > 0) {
      sections.add(new Section(currentHeading, currentHeading + "\n\n" + currentText));
    }
    return sections;
  }

  /** Splits a text block into chunks of at most {@code maxSize} characters with overlap. */
  private List<String> splitBySize(String text, int maxSize, int overlap) {
    if (text.length() <= maxSize) {
      return List.of(text);
    }

    List<String> chunks = new ArrayList<>();
    int start = 0;
    while (start < text.length()) {
      int end = Math.min(start + maxSize, text.length());
      chunks.add(text.substring(start, end));
      if (end >= text.length()) {
        break;
      }
      start = end - overlap;
      if (start < 0) {
        start = 0;
      }
    }
    return chunks;
  }

  /** Extracts category and subcategory from the file path. */
  private Map<String, String> extractMetadata(String filePath) {
    Map<String, String> metadata = new LinkedHashMap<>();
    String[] parts = filePath.split("/");
    if (parts.length >= 1) {
      metadata.put("category", parts[0]);
    }
    if (parts.length >= 2) {
      metadata.put("subcategory", parts[1]);
    }
    if (parts.length >= 3) {
      metadata.put("subcategory2", parts[1] + "/" + parts[2]);
    }
    return metadata;
  }

  /** Generates a stable unique id for a chunk. */
  private String generateId(String filePath, int chunkIndex) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest((filePath + "#" + chunkIndex).getBytes(StandardCharsets.UTF_8));
      StringBuilder hex = new StringBuilder();
      for (byte b : hash) {
        hex.append(String.format("%02x", b));
      }
      return hex.substring(0, 16); // 16-char hex id is enough for uniqueness
    } catch (Exception e) {
      // Fallback: use hashCode
      return Integer.toHexString((filePath + "#" + chunkIndex).hashCode());
    }
  }

  /** A heading section extracted from the Markdown. */
  private record Section(String heading, String text) {}
}
