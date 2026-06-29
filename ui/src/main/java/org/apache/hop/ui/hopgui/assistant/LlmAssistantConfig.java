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

package org.apache.hop.ui.hopgui.assistant;

import org.apache.commons.lang3.StringUtils;

/**
 * Configuration for the built-in LLM personal assistant, sourced exclusively from environment
 * variables (and matching system properties as a fallback).
 *
 * <p>Supported variables:
 *
 * <ul>
 *   <li><strong>HOP_LLM_ENABLED</strong> &ndash; {@code true}/{@code false} to show or hide the
 *       assistant floating button (default: {@code false}).
 *   <li><strong>HOP_LLM_API_URL</strong> &ndash; Base URL or full chat-completions endpoint of an
 *       OpenAI-compatible LLM service.
 *   <li><strong>HOP_LLM_API_KEY</strong> &ndash; Secret API key sent as {@code Authorization:
 *       Bearer <key>}.
 *   <li><strong>HOP_LLM_MODEL</strong> &ndash; The model id to use (e.g. {@code gpt-4o-mini}).
 *   <li><strong>HOP_LLM_SYSTEM_PROMPT</strong> &ndash; Optional system prompt (defaults to a Hop
 *       assistant prompt).
 *   <li><strong>HOP_LLM_TIMEOUT_SECONDS</strong> &ndash; Request timeout in seconds (default 60).
 * </ul>
 *
 * Keeping these values in the environment avoids committing secrets to source control while still
 * allowing per-deployment configuration of the desktop (RCP) and web (RAP) clients.
 */
public final class LlmAssistantConfig {

  public static final String ENV_ENABLED = "HOP_LLM_ENABLED";
  public static final String ENV_API_URL = "HOP_LLM_API_URL";
  public static final String ENV_API_KEY = "HOP_LLM_API_KEY";
  public static final String ENV_MODEL = "HOP_LLM_MODEL";
  public static final String ENV_SYSTEM_PROMPT = "HOP_LLM_SYSTEM_PROMPT";
  public static final String ENV_TIMEOUT_SECONDS = "HOP_LLM_TIMEOUT_SECONDS";

  private static final String DEFAULT_SYSTEM_PROMPT =
      "You are the Apache Hop personal assistant. You help users with ETL pipelines, "
          + "transforms, workflows, metadata and general data orchestration questions. "
          + "Be concise and practical.";

  private final boolean enabled;
  private final String apiUrl;
  private final String apiKey;
  private final String model;
  private final String systemPrompt;
  private final int timeoutSeconds;

  private static LlmAssistantConfig instance;

  private LlmAssistantConfig() {
    this.enabled = Boolean.parseBoolean(resolve(ENV_ENABLED, "false"));
    this.apiUrl = resolve(ENV_API_URL, "");
    this.apiKey = resolve(ENV_API_KEY, "");
    this.model = resolve(ENV_MODEL, "");
    this.systemPrompt = resolve(ENV_SYSTEM_PROMPT, DEFAULT_SYSTEM_PROMPT);
    this.timeoutSeconds = parseInt(resolve(ENV_TIMEOUT_SECONDS, "60"), 60);
  }

  /** Returns the singleton configuration instance. */
  public static synchronized LlmAssistantConfig getInstance() {
    if (instance == null) {
      instance = new LlmAssistantConfig();
    }
    return instance;
  }

  /**
   * Whether the assistant floating button should be shown. The button only renders when this is
   * {@code true} <em>and</em> an API URL is configured.
   */
  public boolean isAvailable() {
    return enabled && StringUtils.isNotBlank(apiUrl);
  }

  public boolean isEnabled() {
    return enabled;
  }

  public String getApiUrl() {
    return apiUrl;
  }

  /**
   * Returns the full chat-completions endpoint. If the configured URL already points at a
   * chat-completions resource it is used as-is, otherwise {@code /chat/completions} is appended so
   * a base URL such as {@code https://api.openai.com/v1} works out of the box.
   */
  public String getChatCompletionsUrl() {
    if (StringUtils.isBlank(apiUrl)) {
      return "";
    }
    String url = apiUrl.trim();
    if (url.endsWith("/")) {
      url = url.substring(0, url.length() - 1);
    }
    if (url.endsWith("/chat/completions")) {
      return url;
    }
    return url + "/chat/completions";
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getModel() {
    return model;
  }

  public String getSystemPrompt() {
    return systemPrompt;
  }

  public int getTimeoutSeconds() {
    return timeoutSeconds;
  }

  /** Resolve a value from system properties first, then environment variables, then default. */
  private static String resolve(String key, String defaultValue) {
    String value = System.getProperty(key);
    if (StringUtils.isBlank(value)) {
      value = System.getenv(key);
    }
    return StringUtils.isBlank(value) ? defaultValue : value;
  }

  private static int parseInt(String value, int defaultValue) {
    try {
      return Integer.parseInt(value.trim());
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }
}
