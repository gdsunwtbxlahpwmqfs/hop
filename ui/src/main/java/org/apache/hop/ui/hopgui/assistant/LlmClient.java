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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.hop.core.json.HopJson;
import org.apache.hop.i18n.BaseMessages;

/**
 * Minimal OpenAI-compatible chat completions client used by the personal assistant. It builds the
 * request from the configured {@link LlmAssistantConfig} and a rolling conversation history, then
 * returns the assistant reply text.
 *
 * <p>The client always speaks the OpenAI wire format. To support multiple/non-OpenAI providers,
 * point {@code HOP_LLM_API_URL} at a <a href="https://docs.litellm.ai/docs/proxy/server">LiteLLM
 * proxy</a> which translates between formats; Hop does not need to know which upstream provider is
 * actually used.
 */
public class LlmClient {

  private static final Class<?> PKG = LlmAssistantDialog.class;

  /** A single chat message in the conversation. */
  public record ChatMessage(String role, String content) {}

  private final LlmAssistantConfig config;
  private final HttpClient httpClient;

  public LlmClient(LlmAssistantConfig config) {
    this.config = config;
    this.httpClient =
        HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(Math.max(5, config.getTimeoutSeconds())))
            .build();
  }

  /**
   * Sends the conversation to the configured LLM endpoint and returns the assistant reply.
   *
   * @param history the prior turns (excluding the system prompt which is added automatically)
   * @return the assistant reply text
   * @throws Exception if the request fails or the response cannot be parsed
   */
  public String chat(List<ChatMessage> history) throws Exception {
    ObjectMapper mapper = HopJson.newMapper();

    ObjectNode body = mapper.createObjectNode();
    body.put("model", config.getModel());
    body.put("stream", false);

    ArrayNode messages = body.putArray("messages");
    // System prompt first
    if (StringUtils.isNotBlank(config.getSystemPrompt())) {
      messages.addObject().put("role", "system").put("content", config.getSystemPrompt());
    }
    for (ChatMessage msg : history) {
      messages.addObject().put("role", msg.role()).put("content", msg.content());
    }

    byte[] payload = mapper.writeValueAsBytes(body);

    HttpRequest.Builder requestBuilder =
        HttpRequest.newBuilder()
            .uri(URI.create(config.getChatCompletionsUrl()))
            .timeout(Duration.ofSeconds(config.getTimeoutSeconds()))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofByteArray(payload));

    if (StringUtils.isNotBlank(config.getApiKey())) {
      requestBuilder.header("Authorization", "Bearer " + config.getApiKey());
    }

    HttpResponse<byte[]> response =
        httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofByteArray());

    int status = response.statusCode();
    byte[] responseBytes = response.body();
    String responseBody =
        responseBytes == null ? "" : new String(responseBytes, StandardCharsets.UTF_8);

    if (status < 200 || status >= 300) {
      throw new IllegalStateException(
          BaseMessages.getString(
              PKG, "LlmAssistant.Error.HttpError", status, truncate(responseBody, 500)));
    }

    JsonNode root = mapper.readTree(responseBody);
    JsonNode choices = root.path("choices");
    if (!choices.isArray() || choices.isEmpty()) {
      // Some servers reply with a top-level "content" or "message"; fall back gracefully.
      String direct = root.path("content").asText("");
      if (StringUtils.isNotBlank(direct)) {
        return direct;
      }
      throw new IllegalStateException(
          BaseMessages.getString(
              PKG, "LlmAssistant.Error.InvalidResponse", truncate(responseBody, 500)));
    }

    JsonNode message = choices.get(0).path("message").path("content");
    String reply = message.asText("").trim();
    if (StringUtils.isBlank(reply)) {
      throw new IllegalStateException(
          BaseMessages.getString(
              PKG, "LlmAssistant.Error.EmptyReply", truncate(responseBody, 500)));
    }
    return reply;
  }

  private static String truncate(String value, int max) {
    if (value == null) {
      return "";
    }
    return value.length() <= max ? value : value.substring(0, max) + "...";
  }

  /** Convenience builder for a mutable conversation history. */
  public static List<ChatMessage> newHistory() {
    return new ArrayList<>();
  }
}
