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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.hop.core.json.HopJson;
import org.apache.hop.core.logging.ILogChannel;
import org.apache.hop.core.logging.LogChannel;
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
  private final ILogChannel log;

  public LlmClient(LlmAssistantConfig config) {
    this.config = config;
    this.httpClient =
        HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(Math.max(5, config.getTimeoutSeconds())))
            .build();
    this.log = new LogChannel("LlmClient");
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
    if (StringUtils.isNotBlank(config.getSystemPrompt())) {
      messages.addObject().put("role", "system").put("content", config.getSystemPrompt());
    }
    for (ChatMessage msg : history) {
      messages.addObject().put("role", msg.role()).put("content", msg.content());
    }

    byte[] payload = mapper.writeValueAsBytes(body);
    String requestBody = new String(payload, StandardCharsets.UTF_8);

    log.logDetailed("LLM request - URL: " + config.getChatCompletionsUrl());
    log.logDetailed("LLM request - Model: " + config.getModel());
    log.logDetailed("LLM request - Messages count: " + messages.size());
    log.logDebug("LLM request - Body: " + truncate(requestBody, 2000));

    Instant startTime = Instant.now();

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

    log.logDebug("Sending LLM request...");

    HttpResponse<byte[]> response =
        httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofByteArray());

    int status = response.statusCode();
    byte[] responseBytes = response.body();
    String responseBody =
        responseBytes == null ? "" : new String(responseBytes, StandardCharsets.UTF_8);

    long durationMs = Duration.between(startTime, Instant.now()).toMillis();
    log.logDetailed("LLM response - Status: " + status + ", Duration: " + durationMs + "ms");
    log.logDebug("LLM response - Body: " + truncate(responseBody, 2000));

    if (status < 200 || status >= 300) {
      log.logError("LLM HTTP error: " + status + " - " + truncate(responseBody, 500));
      throw new IllegalStateException(
          BaseMessages.getString(
              PKG, "LlmAssistant.Error.HttpError", status, truncate(responseBody, 500)));
    }

    JsonNode root = mapper.readTree(responseBody);
    JsonNode choices = root.path("choices");
    if (!choices.isArray() || choices.isEmpty()) {
      String direct = root.path("content").asText("");
      if (StringUtils.isNotBlank(direct)) {
        log.logDetailed("LLM reply - Direct content, length: " + direct.length());
        return direct;
      }
      log.logError("LLM invalid response: " + truncate(responseBody, 500));
      throw new IllegalStateException(
          BaseMessages.getString(
              PKG, "LlmAssistant.Error.InvalidResponse", truncate(responseBody, 500)));
    }

    JsonNode message = choices.get(0).path("message").path("content");
    String reply = message.asText("").trim();
    if (StringUtils.isBlank(reply)) {
      log.logError("LLM empty reply: " + truncate(responseBody, 500));
      throw new IllegalStateException(
          BaseMessages.getString(
              PKG, "LlmAssistant.Error.EmptyReply", truncate(responseBody, 500)));
    }
    log.logDetailed("LLM reply - Length: " + reply.length() + ", Duration: " + durationMs + "ms");
    return reply;
  }

  public interface StreamCallback {
    void onChunk(String chunk);

    void onReasoning(String chunk);

    void onComplete(String fullResponse);

    void onError(Exception e);
  }

  public void streamChat(
      List<ChatMessage> history, AtomicBoolean cancelled, StreamCallback callback)
      throws Exception {
    streamChat(history, null, cancelled, callback);
  }

  /**
   * Streams a chat response with an optional extra context string appended to the system prompt.
   * Used by the RAG subsystem to inject retrieved knowledge base chunks.
   *
   * @param history the prior turns (excluding the system prompt)
   * @param extraContext optional text appended to the system prompt (e.g. RAG context), may be null
   * @param cancelled cancellation flag
   * @param callback stream callback
   */
  public void streamChat(
      List<ChatMessage> history,
      String extraContext,
      AtomicBoolean cancelled,
      StreamCallback callback)
      throws Exception {
    log.logDebug("=== LLM streamChat START ===");
    if (cancelled != null && cancelled.get()) {
      log.logDebug("Stream chat cancelled before start");
      return;
    }

    ObjectMapper mapper = HopJson.newMapper();

    ObjectNode body = mapper.createObjectNode();
    body.put("model", config.getModel());
    body.put("stream", true);

    ArrayNode messages = body.putArray("messages");
    String systemPrompt = config.getSystemPrompt();
    if (StringUtils.isNotBlank(extraContext)) {
      systemPrompt =
          StringUtils.isNotBlank(systemPrompt) ? systemPrompt + extraContext : extraContext;
    }
    if (StringUtils.isNotBlank(systemPrompt)) {
      messages.addObject().put("role", "system").put("content", systemPrompt);
    }
    for (ChatMessage msg : history) {
      messages.addObject().put("role", msg.role()).put("content", msg.content());
    }

    byte[] payload = mapper.writeValueAsBytes(body);
    String requestBody = new String(payload, StandardCharsets.UTF_8);

    log.logDetailed("LLM stream request - URL: " + config.getChatCompletionsUrl());
    log.logDetailed("LLM stream request - Model: " + config.getModel());
    log.logDetailed("LLM stream request - Messages count: " + messages.size());
    log.logDetailed(
        "LLM stream request - API Key present: " + StringUtils.isNotBlank(config.getApiKey()));
    log.logDebug("LLM stream request - Body: " + truncate(requestBody, 2000));

    Instant startTime = Instant.now();

    HttpRequest.Builder requestBuilder =
        HttpRequest.newBuilder()
            .uri(URI.create(config.getChatCompletionsUrl()))
            .timeout(Duration.ofSeconds(config.getTimeoutSeconds()))
            .header("Content-Type", "application/json")
            .header("Accept", "text/event-stream")
            .POST(HttpRequest.BodyPublishers.ofByteArray(payload));

    if (StringUtils.isNotBlank(config.getApiKey())) {
      requestBuilder.header("Authorization", "Bearer " + config.getApiKey());
    }

    log.logDebug("Sending LLM stream request...");

    HttpResponse<Stream<String>> response =
        httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofLines());

    int status = response.statusCode();
    log.logDetailed("LLM stream response - Status: " + status);

    if (status < 200 || status >= 300) {
      StringBuilder errorBody = new StringBuilder();
      response.body().forEach(errorBody::append);
      log.logError(
          "LLM stream HTTP error: " + status + " - " + truncate(errorBody.toString(), 500));
      throw new IllegalStateException(
          BaseMessages.getString(
              PKG, "LlmAssistant.Error.HttpError", status, truncate(errorBody.toString(), 500)));
    }

    Iterator<String> lines = response.body().iterator();
    StringBuilder fullResponse = new StringBuilder();
    int chunkCount = 0;
    int reasoningChunkCount = 0;
    Instant firstChunkTime = null;

    log.logDebug("Starting to process stream chunks...");

    while (lines.hasNext()) {
      if (cancelled != null && cancelled.get()) {
        log.logDebug("Stream chat cancelled during processing");
        break;
      }

      String line = lines.next();
      if (line.isEmpty()) {
        continue;
      }
      if (line.equals("data: [DONE]")) {
        log.logDebug("Stream done marker received");
        break;
      }
      if (!line.startsWith("data: ")) {
        log.logDebug("Non-data line in stream: " + truncate(line, 200));
        continue;
      }

      String data = line.substring("data: ".length());
      if (data.isEmpty()) {
        continue;
      }

      try {
        JsonNode root = mapper.readTree(data);
        JsonNode choices = root.path("choices");
        if (!choices.isArray() || choices.isEmpty()) {
          continue;
        }

        JsonNode delta = choices.get(0).path("delta");

        // 1) reasoning_content (thinking process)
        String reasoning = delta.path("reasoning_content").asText("");
        if (StringUtils.isNotBlank(reasoning)) {
          reasoningChunkCount++;
          log.logDebug(
              "LLM reasoning chunk #" + reasoningChunkCount + ", length: " + reasoning.length());
          callback.onReasoning(reasoning);
        }

        // 2) content (actual reply)
        String content = delta.path("content").asText("");
        if (StringUtils.isNotBlank(content)) {
          if (firstChunkTime == null) {
            firstChunkTime = Instant.now();
            long timeToFirstChunk = Duration.between(startTime, firstChunkTime).toMillis();
            log.logDetailed("LLM first content chunk received in " + timeToFirstChunk + "ms");
          }
          chunkCount++;
          fullResponse.append(content);
          log.logDebug(
              "LLM chunk #"
                  + chunkCount
                  + ", length: "
                  + content.length()
                  + ", content: "
                  + truncate(content, 50));
          callback.onChunk(content);
        }

        String finishReason = choices.get(0).path("finish_reason").asText("");
        if (StringUtils.isNotBlank(finishReason)) {
          log.logDebug("Stream finish reason: " + finishReason);
          break;
        }
      } catch (Exception e) {
        log.logDebug(
            "Failed to parse stream line: " + truncate(data, 200) + ", error: " + e.getMessage());
      }
    }

    long totalDurationMs = Duration.between(startTime, Instant.now()).toMillis();
    String finalReply = fullResponse.toString().trim();
    boolean isCancelled = cancelled != null && cancelled.get();
    log.logDebug("=== LLM streamChat END ===");
    log.logDetailed(
        "LLM stream complete - Content chunks: "
            + chunkCount
            + ", Reasoning chunks: "
            + reasoningChunkCount
            + ", Total length: "
            + finalReply.length()
            + ", Total duration: "
            + totalDurationMs
            + "ms"
            + ", Cancelled: "
            + isCancelled);

    if (isCancelled) {
      log.logDebug("LLM stream cancelled");
      callback.onError(new IllegalStateException("Cancelled"));
    } else if (StringUtils.isBlank(finalReply)) {
      log.logDetailed("LLM stream returned empty reply");
      callback.onError(
          new IllegalStateException(
              BaseMessages.getString(PKG, "LlmAssistant.Error.EmptyReply", "")));
    } else {
      log.logDebug("Calling onComplete with reply length: " + finalReply.length());
      callback.onComplete(finalReply);
    }
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
