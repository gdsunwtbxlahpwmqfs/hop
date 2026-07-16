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
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.hop.core.logging.ILogChannel;
import org.apache.hop.core.logging.LogChannel;

/**
 * Manages built-in skills bundled with the JAR. On first run (or when missing), these are extracted
 * from classpath resources into the project-level skills directory.
 *
 * <p>Once installed, built-in skills are regular {@code SKILL.md} files that the user can disable
 * or modify but not delete.
 */
public class BuiltinSkills {

  private static final ILogChannel log = new LogChannel("BuiltinSkills");

  /** Resource prefix for built-in skills. */
  private static final String RESOURCE_PREFIX =
      "/org/apache/hop/ui/hopgui/assistant/skills/builtin/";

  /** Built-in skill names. */
  public static final List<String> BUILTIN_NAMES =
      List.of(
          "hop-quickstart", "pipeline-design-patterns", "pipeline-debugger", "performance-tuning");

  private static final String SKILL_FILE = "SKILL.md";

  /**
   * Ensures all built-in skills are present in the target skills directory. Missing skills are
   * extracted from classpath resources; existing files are left untouched (user may have customized
   * them).
   *
   * @param skillsDir the project-level skills directory
   */
  public void ensureInstalled(Path skillsDir) {
    if (skillsDir == null) return;

    for (String name : BUILTIN_NAMES) {
      Path skillDir = skillsDir.resolve(name);
      Path skillFile = skillDir.resolve(SKILL_FILE);
      if (Files.isRegularFile(skillFile)) {
        continue; // Already installed — respect user modifications
      }
      try {
        String content = loadResource(RESOURCE_PREFIX + name + "/" + SKILL_FILE);
        if (content != null) {
          Files.createDirectories(skillDir);
          Files.writeString(skillFile, content, StandardCharsets.UTF_8);
          log.logBasic("Installed built-in skill: " + name);
        }
      } catch (IOException e) {
        log.logError("Failed to install built-in skill: " + name, e);
      }
    }
  }

  /**
   * Restores a built-in skill to its original content, overwriting user modifications.
   *
   * @param skillsDir the project-level skills directory
   * @param name the built-in skill name
   * @return {@code true} if the skill was restored
   */
  public boolean restore(Path skillsDir, String name) {
    if (!BUILTIN_NAMES.contains(name)) return false;
    Path skillFile = skillsDir.resolve(name).resolve(SKILL_FILE);
    String content = loadResource(RESOURCE_PREFIX + name + "/" + SKILL_FILE);
    if (content == null) return false;
    try {
      Files.createDirectories(skillFile.getParent());
      Files.writeString(skillFile, content, StandardCharsets.UTF_8);
      log.logBasic("Restored built-in skill: " + name);
      return true;
    } catch (IOException e) {
      log.logError("Failed to restore built-in skill: " + name, e);
      return false;
    }
  }

  /**
   * Restores all built-in skills to their original content.
   *
   * @param skillsDir the project-level skills directory
   */
  public void restoreAll(Path skillsDir) {
    for (String name : BUILTIN_NAMES) {
      restore(skillsDir, name);
    }
  }

  /** Loads a classpath resource as a UTF-8 string. */
  private String loadResource(String path) {
    try (InputStream in = getClass().getResourceAsStream(path)) {
      if (in == null) {
        log.logError("Built-in skill resource not found: " + path);
        return null;
      }
      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      log.logError("Failed to load resource: " + path, e);
      return null;
    }
  }
}
