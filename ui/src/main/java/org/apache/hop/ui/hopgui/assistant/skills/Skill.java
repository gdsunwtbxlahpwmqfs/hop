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

import java.nio.file.Path;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * A reusable AI assistant skill, stored as a {@code SKILL.md} file (YAML frontmatter + Markdown
 * body). The format is compatible with OpenCode skills so both tools can share the same files.
 *
 * <p>OpenCode reads only {@code name} and {@code description}; Hop-specific fields use the {@code
 * hop_} prefix and are ignored by OpenCode.
 */
@Getter
@Setter
public class Skill {

  /** Trigger type: when the skill is activated. */
  public enum TriggerType {
    /** Manually selected by the user in the assistant dialog. */
    MANUAL,
    /** Always active — injected into every conversation. */
    GLOBAL
  }

  // ── OpenCode standard fields ──

  /** Skill name in kebab-case; must match the directory name. */
  private String name;

  /** When to trigger this skill (OpenCode primary matching mechanism). */
  private String description;

  // ── Hop extension fields (hop_ prefix in YAML) ──

  private String category = "custom";
  private boolean enabled = true;
  private TriggerType trigger = TriggerType.MANUAL;
  private int priority = 0;
  private String source = "custom";
  private String version = "1.0.0";
  private int usageCount = 0;
  private Instant updatedAt;

  // ── Runtime fields (not persisted in frontmatter) ──

  /** Filesystem directory containing this skill's SKILL.md. */
  private transient Path directory;

  /** Markdown body (everything after the YAML frontmatter). */
  private transient String content = "";

  /** Constructs a new empty skill. */
  public Skill() {}

  /**
   * Constructs a skill with the given name, description, and content.
   *
   * @param name kebab-case skill name
   * @param description trigger description
   * @param content markdown body
   */
  public Skill(String name, String description, String content) {
    this.name = name;
    this.description = description;
    this.content = content;
    this.updatedAt = Instant.now();
  }

  /**
   * Checks whether this skill is built-in (cannot be deleted, only disabled).
   *
   * @return {@code true} if this is a built-in skill
   */
  public boolean isBuiltin() {
    return "builtin".equals(source);
  }

  /**
   * Returns the approximate token count of the content (rough: 1 token ≈ 4 chars).
   *
   * @return estimated token count
   */
  public int estimateTokens() {
    if (content == null || content.isEmpty()) return 0;
    return content.length() / 4;
  }
}
