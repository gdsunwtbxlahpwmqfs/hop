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
import org.apache.hop.core.logging.ILogChannel;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.ui.hopgui.assistant.LlmAssistantConfig;

/**
 * Client for the OpenAI-compatible {@code /embeddings} endpoint, proxied through LiteLLM. Reuses
 * the same API URL and key as {@link LlmAssistantConfig}.
 */
public class EmbeddingClient {

  private static final LogChannel log = new LogChannel("EmbeddingClient");

  private final HttpClient httpClient;
  private final LlmAssistantConfig llmConfig;
  private final KnowledgeBaseConfig kbConfig;

  public EmbeddingClient(LlmAssistantConfig llmConfig, KnowledgeBaseConfig kbConfig) {
    this.llmConfig = llmConfig;
    this.kbConfig = kbConfig;
    this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();
  }

  /**
   * Embeds a single text input.
   *
   * @param text the input text
   * @return the embedding vector
   * @throws Exception if the request fails
   */
  public float[] embed(String text) throws Exception {
    List<float[]> results = embedBatch(List.of(text));
    return results.get(0);
  }

  /**
   * Embeds a batch of text inputs. Uses the OpenAI-compatible {@code /embeddings} endpoint.
   *
   * @param texts the input texts
   * @return a list of embedding vectors, one per input
   * @throws Exception if the request fails
   */
  public List<float[]> embedBatch(List<String> texts) throws Exception {
    if (texts.isEmpty()) {
      return List.of();
    }

    ObjectMapper mapper = HopJson.newMapper();
    ObjectNode body = mapper.createObjectNode();
    body.put("model", kbConfig.getEmbeddingModel());

    ArrayNode inputArray = body.putArray("input");
    for (String text : texts) {
      inputArray.add(text);
    }

    byte[] payload = mapper.writeValueAsBytes(body);
    String endpoint = getEmbeddingsUrl();

    log.logDetailed("Embedding request - Endpoint: " + endpoint);
    log.logDetailed("Embedding request - Model: " + kbConfig.getEmbeddingModel());
    log.logDetailed("Embedding request - Input count: " + texts.size());

    HttpRequest.Builder requestBuilder =
        HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .timeout(Duration.ofSeconds(60))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofByteArray(payload));

    if (StringUtils.isNotBlank(llmConfig.getApiKey())) {
      requestBuilder.header("Authorization", "Bearer " + llmConfig.getApiKey());
    }

    HttpResponse<byte[]> response =
        httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofByteArray());

    int status = response.statusCode();
    String responseBody =
        response.body() == null ? "" : new String(response.body(), StandardCharsets.UTF_8);

    if (status < 200 || status >= 300) {
      throw new IllegalStateException(
          "Embedding API error: " + status + " - " + truncate(responseBody, 500));
    }

    JsonNode root = mapper.readTree(responseBody);
    JsonNode dataNode = root.path("data");
    if (!dataNode.isArray() || dataNode.isEmpty()) {
      throw new IllegalStateException("Embedding API returned no data");
    }

    List<float[]> results = new ArrayList<>(dataNode.size());
    for (JsonNode item : dataNode) {
      JsonNode embedding = item.path("embedding");
      if (!embedding.isArray()) {
        throw new IllegalStateException("Embedding API returned invalid embedding");
      }
      float[] vector = new float[embedding.size()];
      for (int i = 0; i < embedding.size(); i++) {
        vector[i] = (float) embedding.get(i).asDouble();
      }
      results.add(vector);
    }

    log.logDetailed("Embedding response - Vectors: " + results.size());
    return results;
  }

  /**
   * Returns the embedding dimension by making a test embedding call. Used during collection
   * creation.
   */
  public int getEmbeddingDimension() throws Exception {
    float[] testVector = embed("dimension test");
    return testVector.length;
  }

  /** Derives the embeddings endpoint from the LLM API URL. */
  private String getEmbeddingsUrl() {
    String url = llmConfig.getApiUrl().trim();
    if (StringUtils.isBlank(url)) {
      throw new IllegalStateException("HOP_LLM_API_URL is not configured");
    }
    if (url.endsWith("/")) {
      url = url.substring(0, url.length() - 1);
    }
    // Strip known suffixes to get the base URL
    if (url.endsWith("/chat/completions")) {
      url = url.substring(0, url.length() - "/chat/completions".length());
    }
    return url + "/embeddings";
  }

  ILogChannel getLog() {
    return log;
  }

  private static String truncate(String value, int max) {
    if (value == null) {
      return "";
    }
    return value.length() <= max ? value : value.substring(0, max) + "...";
  }
}
