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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import org.apache.hop.core.logging.ILogChannel;
import org.apache.hop.core.logging.LogChannel;

/**
 * Central manager for loading, searching, and activating skills. Skills are loaded from the project
 * directory ({@code .opencode/skills/}) and the global directory ({@code
 * ~/.config/opencode/skills/}).
 *
 * <p>Skills with trigger {@link Skill.TriggerType#GLOBAL} are always active (if enabled). Manual
 * skills are activated by the user via {@link #activateManual(String)}.
 */
public class SkillManager {

  private static final ILogChannel log = new LogChannel("SkillManager");

  /** Project-level skills directory (relative to the current working directory). */
  public static final Path PROJECT_SKILLS_DIR = Path.of(".opencode", "skills");

  /** Global skills directory (shared across projects). */
  public static final Path GLOBAL_SKILLS_DIR =
      Path.of(System.getProperty("user.home"), ".config", "opencode", "skills");

  private static SkillManager instance;

  private final List<Skill> skills = new CopyOnWriteArrayList<>();
  private final Set<String> activeManualSkills = new ConcurrentSkipListSet<>();
  private final SkillStorage storage;

  private SkillManager() {
    this.storage = new SkillStorage();
  }

  /** Returns the singleton manager instance. */
  public static synchronized SkillManager getInstance() {
    if (instance == null) {
      instance = new SkillManager();
    }
    return instance;
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Loading
  // ──────────────────────────────────────────────────────────────────────────────

  /** Reloads all skills from both project and global directories. */
  public void loadAll() {
    // Ensure built-in skills are installed before loading
    new BuiltinSkills().ensureInstalled(PROJECT_SKILLS_DIR);

    skills.clear();
    List<Skill> projectSkills = storage.loadFrom(PROJECT_SKILLS_DIR);
    List<Skill> globalSkills = storage.loadFrom(GLOBAL_SKILLS_DIR);

    // Deduplicate by name: project skills take precedence
    Set<String> projectNames =
        projectSkills.stream().map(Skill::getName).collect(java.util.stream.Collectors.toSet());
    skills.addAll(projectSkills);
    globalSkills.stream().filter(s -> !projectNames.contains(s.getName())).forEach(skills::add);

    // Clean up active manual skills that no longer exist
    activeManualSkills.retainAll(
        skills.stream().map(Skill::getName).collect(java.util.stream.Collectors.toSet()));

    log.logBasic(
        "Loaded "
            + skills.size()
            + " skills ("
            + projectSkills.size()
            + " project, "
            + globalSkills.size()
            + " global)");
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // CRUD
  // ──────────────────────────────────────────────────────────────────────────────

  /**
   * Creates a new skill and saves it to the project directory.
   *
   * @param name kebab-case skill name
   * @param description trigger description
   * @param content markdown body
   * @return the created skill
   */
  public Skill create(String name, String description, String content) {
    Skill skill = new Skill(name, description, content);
    skill.setSource("custom");
    skill.setEnabled(false);
    skill.setUpdatedAt(java.time.Instant.now());
    try {
      storage.save(skill, PROJECT_SKILLS_DIR);
      skills.add(skill);
    } catch (IOException e) {
      log.logError("Failed to create skill: " + name, e);
    }
    return skill;
  }

  /**
   * Saves an updated skill.
   *
   * @param skill the skill to update
   */
  public void update(Skill skill) {
    try {
      storage.save(skill, PROJECT_SKILLS_DIR);
      // Replace in list
      for (int i = 0; i < skills.size(); i++) {
        if (skills.get(i).getName().equals(skill.getName())) {
          skills.set(i, skill);
          break;
        }
      }
    } catch (IOException e) {
      log.logError("Failed to update skill: " + skill.getName(), e);
    }
  }

  /**
   * Deletes a skill by name. Built-in skills cannot be deleted.
   *
   * @param name skill name
   */
  public void delete(String name) {
    Skill skill = find(name);
    if (skill == null || skill.isBuiltin()) return;
    try {
      storage.delete(skill.getDirectory());
      skills.removeIf(s -> s.getName().equals(name));
      activeManualSkills.remove(name);
    } catch (IOException e) {
      log.logError("Failed to delete skill: " + name, e);
    }
  }

  /**
   * Toggles the enabled state of a skill.
   *
   * @param name skill name
   */
  public void toggleEnabled(String name) {
    Skill skill = find(name);
    if (skill == null) return;
    skill.setEnabled(!skill.isEnabled());
    update(skill);
  }

  /**
   * Duplicates a skill with a new name.
   *
   * @param sourceName the skill to copy
   * @param newName the new skill name
   * @return the copied skill, or {@code null} if the source was not found
   */
  public Skill duplicate(String sourceName, String newName) {
    Skill source = find(sourceName);
    if (source == null) return null;
    Skill copy = new Skill(newName, source.getDescription(), source.getContent());
    copy.setCategory(source.getCategory());
    copy.setTrigger(source.getTrigger());
    copy.setPriority(source.getPriority());
    copy.setSource("custom");
    copy.setVersion("1.0.0");
    try {
      storage.save(copy, PROJECT_SKILLS_DIR);
      skills.add(copy);
    } catch (IOException e) {
      log.logError("Failed to duplicate skill: " + sourceName, e);
    }
    return copy;
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Querying
  // ──────────────────────────────────────────────────────────────────────────────

  /** Returns all loaded skills. */
  public List<Skill> getAll() {
    return new ArrayList<>(skills);
  }

  /** Finds a skill by name. */
  public Skill find(String name) {
    return skills.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
  }

  /**
   * Searches skills by query string, category, and enabled state.
   *
   * @param query text to search in name and description (null/blank = no filter)
   * @param category category filter (null = all)
   * @param enabledOnly if true, only return enabled skills
   * @return filtered and sorted list
   */
  public List<Skill> search(String query, String category, Boolean enabledOnly) {
    Stream<Skill> stream = skills.stream();
    if (query != null && !query.isBlank()) {
      String q = query.toLowerCase();
      stream =
          stream.filter(
              s ->
                  s.getName().toLowerCase().contains(q)
                      || (s.getDescription() != null
                          && s.getDescription().toLowerCase().contains(q)));
    }
    if (category != null && !category.isBlank() && !"all".equalsIgnoreCase(category)) {
      stream = stream.filter(s -> category.equalsIgnoreCase(s.getCategory()));
    }
    if (Boolean.TRUE.equals(enabledOnly)) {
      stream = stream.filter(Skill::isEnabled);
    }
    return stream.sorted(Comparator.comparing(Skill::getName)).toList();
  }

  /** Returns global skills (always active when enabled). */
  public List<Skill> getGlobalSkills() {
    return skills.stream()
        .filter(s -> s.getTrigger() == Skill.TriggerType.GLOBAL)
        .sorted(Comparator.comparingInt(Skill::getPriority).reversed())
        .toList();
  }

  /** Returns manual skills (require user activation). */
  public List<Skill> getManualSkills() {
    return skills.stream()
        .filter(s -> s.getTrigger() == Skill.TriggerType.MANUAL)
        .sorted(Comparator.comparing(Skill::getName))
        .toList();
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Activation
  // ──────────────────────────────────────────────────────────────────────────────

  /** Activates a manual skill for the current session. */
  public void activateManual(String name) {
    if (find(name) != null) {
      activeManualSkills.add(name);
    }
  }

  /** Deactivates a manual skill. */
  public void deactivateManual(String name) {
    activeManualSkills.remove(name);
  }

  /** Checks if a manual skill is currently activated. */
  public boolean isManualActive(String name) {
    return activeManualSkills.contains(name);
  }

  /** Clears all manual skill activations. */
  public void clearManualActivations() {
    activeManualSkills.clear();
  }

  /**
   * Returns all skills active in the current session: enabled global skills + activated manual
   * skills.
   */
  public List<Skill> getActiveSkills() {
    return skills.stream()
        .filter(Skill::isEnabled)
        .filter(
            s ->
                s.getTrigger() == Skill.TriggerType.GLOBAL
                    || activeManualSkills.contains(s.getName()))
        .sorted(Comparator.comparingInt(Skill::getPriority).reversed())
        .toList();
  }

  /**
   * Increments the usage count of a skill and persists the change.
   *
   * @param name skill name
   */
  public void incrementUsage(String name) {
    Skill skill = find(name);
    if (skill != null) {
      skill.setUsageCount(skill.getUsageCount() + 1);
      update(skill);
    }
  }

  // ──────────────────────────────────────────────────────────────────────────────
  // Prompt building
  // ──────────────────────────────────────────────────────────────────────────────

  /**
   * Builds an augmented system prompt by appending active skill content to the base prompt.
   *
   * @param basePrompt the default system prompt
   * @return base prompt + active skills content
   */
  public String buildAugmentedPrompt(String basePrompt) {
    List<Skill> active = getActiveSkills();
    if (active.isEmpty()) {
      return basePrompt;
    }
    StringBuilder sb = new StringBuilder(basePrompt);
    for (Skill skill : active) {
      sb.append("\n\n--- Skill: ")
          .append(skill.getName())
          .append(" ---\n")
          .append(skill.getContent());
    }
    return sb.toString();
  }

  /**
   * Builds only the active skill content (without the base prompt). Intended to be used as
   * extraContext appended to the system prompt by {@link
   * org.apache.hop.ui.hopgui.assistant.LlmClient#streamChat}.
   *
   * @return skill content string, or empty string if no skills are active
   */
  public String buildSkillContext() {
    List<Skill> active = getActiveSkills();
    if (active.isEmpty()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (Skill skill : active) {
      sb.append("\n\n--- Skill: ")
          .append(skill.getName())
          .append(" ---\n")
          .append(skill.getContent());
    }
    return sb.toString();
  }

  /** Returns the storage instance (for import/export). */
  public SkillStorage getStorage() {
    return storage;
  }
}
