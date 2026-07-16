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

package org.apache.hop.ui.hopgui.assistant.skills;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.hop.core.logging.ILogChannel;
import org.apache.hop.core.logging.LogChannel;

/**
 * Reads and writes {@code SKILL.md} files (YAML frontmatter + Markdown body) on the local
 * filesystem.
 *
 * <p>The YAML frontmatter is parsed with a lightweight hand-written parser because SnakeYAML is not
 * available in the UI module. Only the subset of YAML used by skill files is supported: simple
 * scalars, booleans, integers, and folded block scalars ({@code >-}).
 */
public class SkillStorage {

  private static final ILogChannel log = new LogChannel("SkillStorage");

  private static final String YAML_DELIMITER = "---";
  private static final String SKILL_FILE = "SKILL.md";

  /**
   * Scans a skills directory and loads every {@code <name>/SKILL.md} found.
   *
   * @param skillsDir root directory containing skill sub-directories
   * @return list of parsed skills (empty if the directory does not exist)
   */
  public List<Skill> loadFrom(Path skillsDir) {
    List<Skill> result = new ArrayList<>();
    if (skillsDir == null || !Files.isDirectory(skillsDir)) {
      return result;
    }
    try (var dirs = Files.list(skillsDir)) {
      dirs.filter(Files::isDirectory)
          .forEach(
              dir -> {
                Path skillFile = dir.resolve(SKILL_FILE);
                if (Files.isRegularFile(skillFile)) {
                  try {
                    Skill skill = parse(skillFile);
                    skill.setDirectory(dir);
                    result.add(skill);
                  } catch (Exception e) {
                    log.logError("Failed to parse skill file: " + skillFile, e);
                  }
                }
              });
    } catch (IOException e) {
      log.logError("Failed to list skills directory: " + skillsDir, e);
    }
    return result;
  }

  /**
   * Parses a single {@code SKILL.md} file.
   *
   * @param skillFile path to the SKILL.md file
   * @return a populated {@link Skill} object
   * @throws IOException if the file cannot be read
   */
  public Skill parse(Path skillFile) throws IOException {
    String raw = Files.readString(skillFile, StandardCharsets.UTF_8);
    return parseString(raw, skillFile.getParent());
  }

  /**
   * Parses SKILL.md content from a string.
   *
   * @param raw the full file content
   * @param directory the skill's directory (set on the returned object)
   * @return a populated skill
   */
  public Skill parseString(String raw, Path directory) {
    Skill skill = new Skill();
    skill.setDirectory(directory);

    String[] parts = splitFrontmatter(raw);
    Map<String, String> frontmatter = parseFrontmatter(parts[0]);
    String body = parts[1].strip();

    skill.setName(frontmatter.getOrDefault("name", ""));
    skill.setDescription(frontmatter.getOrDefault("description", ""));
    skill.setCategory(frontmatter.getOrDefault("hop_category", "custom"));
    skill.setSource(frontmatter.getOrDefault("hop_source", "custom"));
    String enabledStr = frontmatter.get("hop_enabled");
    if (enabledStr != null) {
      skill.setEnabled(parseBool(enabledStr));
    } else {
      skill.setEnabled("builtin".equals(skill.getSource()));
    }
    skill.setTrigger(parseTrigger(frontmatter.getOrDefault("hop_trigger", "manual")));
    skill.setPriority(parseInt(frontmatter.getOrDefault("hop_priority", "0")));
    skill.setUsageCount(parseInt(frontmatter.getOrDefault("hop_usage_count", "0")));
    skill.setContent(body);

    return skill;
  }

  /**
   * Writes a skill to {@code <skillsDir>/<name>/SKILL.md}.
   *
   * @param skill the skill to persist
   * @param skillsDir root skills directory
   * @throws IOException if the file cannot be written
   */
  public void save(Skill skill, Path skillsDir) throws IOException {
    Path dir = skillsDir.resolve(skill.getName());
    Files.createDirectories(dir);
    Path file = dir.resolve(SKILL_FILE);
    skill.setDirectory(dir);
    skill.setUpdatedAt(Instant.now());
    Files.writeString(file, serialize(skill), StandardCharsets.UTF_8);
    log.logBasic("Saved skill: " + file);
  }

  /**
   * Serializes a skill to SKILL.md content.
   *
   * @param skill the skill to serialize
   * @return the full SKILL.md file content
   */
  public String serialize(Skill skill) {
    StringBuilder sb = new StringBuilder();
    sb.append(YAML_DELIMITER).append('\n');
    appendYaml(sb, "name", skill.getName());
    appendYamlBlock(sb, "description", skill.getDescription());
    appendYaml(sb, "hop_category", skill.getCategory());
    appendYaml(sb, "hop_enabled", String.valueOf(skill.isEnabled()));
    appendYaml(sb, "hop_trigger", skill.getTrigger().name().toLowerCase());
    appendYaml(sb, "hop_priority", String.valueOf(skill.getPriority()));
    appendYaml(sb, "hop_source", skill.getSource());
    appendYaml(sb, "hop_version", skill.getVersion());
    appendYaml(sb, "hop_usage_count", String.valueOf(skill.getUsageCount()));
    sb.append(YAML_DELIMITER).append('\n');
    if (skill.getContent() != null && !skill.getContent().isBlank()) {
      sb.append('\n').append(skill.getContent());
    }
    return sb.toString();
  }

  /**
   * Deletes a skill directory.
   *
   * @param skillDir the skill's directory
   * @throws IOException if deletion fails
   */
  public void delete(Path skillDir) throws IOException {
    if (skillDir != null && Files.isDirectory(skillDir)) {
      try (var walk = Files.walk(skillDir)) {
        walk.sorted(java.util.Comparator.reverseOrder())
            .forEach(
                p -> {
                  try {
                    Files.deleteIfExists(p);
                  } catch (IOException e) {
                    log.logError("Failed to delete: " + p, e);
                  }
                });
      }
    }
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // YAML frontmatter parsing helpers
  // ──────────────────────────────────────────────────────────────────────────────

  /**
   * Splits raw content into frontmatter and body.
   *
   * @return array of [frontmatter, body]; frontmatter is empty if no delimiter found
   */
  private String[] splitFrontmatter(String raw) {
    String content = raw.stripLeading();
    if (!content.startsWith(YAML_DELIMITER)) {
      return new String[] {"", raw};
    }
    // Skip opening ---
    int start = content.indexOf('\n');
    if (start < 0) return new String[] {"", raw};
    // Find closing ---
    int end = content.indexOf("\n" + YAML_DELIMITER, start);
    if (end < 0) return new String[] {"", raw};

    String frontmatter = content.substring(start + 1, end);
    String body = content.substring(end + YAML_DELIMITER.length() + 2); // +2 for \n and ---
    return new String[] {frontmatter, body};
  }

  /**
   * Parses frontmatter into a key-value map. Supports simple scalars, folded blocks ({@code >-}),
   * and literal blocks ({@code |}).
   */
  private Map<String, String> parseFrontmatter(String frontmatter) {
    Map<String, String> map = new LinkedHashMap<>();
    if (frontmatter == null || frontmatter.isBlank()) return map;

    String[] lines = frontmatter.split("\n");
    int i = 0;
    while (i < lines.length) {
      String line = lines[i];
      if (line.strip().isEmpty()) {
        i++;
        continue;
      }
      int colon = line.indexOf(':');
      if (colon < 0) {
        i++;
        continue;
      }
      String key = line.substring(0, colon).strip();
      String value = line.substring(colon + 1).strip();

      // Folded block scalar >- or >
      if (value.startsWith(">")) {
        StringBuilder sb = new StringBuilder();
        i++;
        while (i < lines.length && (lines[i].strip().isEmpty() || isMoreIndented(lines[i]))) {
          if (!lines[i].strip().isEmpty()) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(lines[i].strip());
          }
          i++;
        }
        map.put(key, sb.toString().strip());
        continue;
      }

      // Literal block scalar | or |-
      if (value.startsWith("|")) {
        StringBuilder sb = new StringBuilder();
        i++;
        while (i < lines.length && (lines[i].strip().isEmpty() || isMoreIndented(lines[i]))) {
          sb.append(lines[i].stripLeading()).append('\n');
          i++;
        }
        map.put(key, sb.toString().strip());
        continue;
      }

      // Simple scalar — strip surrounding quotes
      if (value.length() >= 2
          && ((value.startsWith("\"") && value.endsWith("\""))
              || (value.startsWith("'") && value.endsWith("'")))) {
        value = value.substring(1, value.length() - 1);
      }
      map.put(key, value);
      i++;
    }
    return map;
  }

  /** Checks if the line is more indented than a top-level YAML key (i.e. part of a block). */
  private boolean isMoreIndented(String line) {
    return line.startsWith("  ") || line.startsWith("\t");
  }

  private boolean parseBool(String value) {
    return "true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
  }

  private int parseInt(String value) {
    try {
      return Integer.parseInt(value.strip());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  private Skill.TriggerType parseTrigger(String value) {
    return "global".equalsIgnoreCase(value) ? Skill.TriggerType.GLOBAL : Skill.TriggerType.MANUAL;
  }

  private void appendYaml(StringBuilder sb, String key, String value) {
    sb.append(key).append(": ").append(value == null ? "" : value).append('\n');
  }

  private void appendYamlBlock(StringBuilder sb, String key, String value) {
    sb.append(key).append(": >-\n");
    if (value != null && !value.isBlank()) {
      sb.append("  ").append(value).append('\n');
    }
  }
}
