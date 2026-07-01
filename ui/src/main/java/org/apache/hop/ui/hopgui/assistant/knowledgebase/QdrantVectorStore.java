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
import java.util.UUID;
import org.apache.hop.core.json.HopJson;
import org.apache.hop.core.logging.LogChannel;

/**
 * Qdrant vector store implementation using the Qdrant REST API. No external SDK dependency is
 * needed; Qdrant's HTTP API fully supports collection management, point upsert, and vector search.
 */
public class QdrantVectorStore implements VectorStore {

  private static final LogChannel log = new LogChannel("QdrantVectorStore");

  private final String qdrantUrl;
  private final String collectionName;
  private final HttpClient httpClient;

  public QdrantVectorStore(KnowledgeBaseConfig config) {
    this.qdrantUrl = config.getQdrantUrl().replaceAll("/$", "");
    this.collectionName = config.getQdrantCollection();
    this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
  }

  @Override
  public void ensureCollection(int vectorSize) throws Exception {
    if (collectionExists()) {
      log.logDetailed("Collection '" + collectionName + "' already exists");
      return;
    }

    ObjectMapper mapper = HopJson.newMapper();
    ObjectNode body = mapper.createObjectNode();
    ObjectNode vectors = body.putObject("vectors");
    vectors.put("size", vectorSize);
    vectors.put("distance", "Cosine");

    sendRequest("PUT", "/collections/" + collectionName, mapper.writeValueAsBytes(body));
    log.logBasic(
        "Created Qdrant collection '" + collectionName + "' with vector size " + vectorSize);
  }

  @Override
  public void upsert(List<KnowledgeChunk> chunks, List<float[]> vectors) throws Exception {
    if (chunks.isEmpty()) {
      return;
    }

    ObjectMapper mapper = HopJson.newMapper();
    ObjectNode body = mapper.createObjectNode();
    ArrayNode points = body.putArray("points");

    for (int i = 0; i < chunks.size(); i++) {
      KnowledgeChunk chunk = chunks.get(i);
      float[] vector = vectors.get(i);

      ObjectNode point = points.addObject();
      // Use UUID for Qdrant point id (must be UUID or unsigned integer)
      point.put(
          "id", UUID.nameUUIDFromBytes(chunk.id().getBytes(StandardCharsets.UTF_8)).toString());

      ArrayNode vectorNode = point.putArray("vector");
      for (float v : vector) {
        vectorNode.add(v);
      }

      ObjectNode payload = point.putObject("payload");
      payload.put("filePath", chunk.filePath());
      payload.put("section", chunk.section());
      payload.put("chunkIndex", chunk.chunkIndex());
      payload.put("content", chunk.content());
      payload.put("chunkId", chunk.id());
      chunk.metadata().forEach(payload::put);
    }

    sendRequest(
        "PUT",
        "/collections/" + collectionName + "/points?wait=true",
        mapper.writeValueAsBytes(body));
    log.logDetailed("Upserted " + chunks.size() + " points to Qdrant");
  }

  @Override
  public List<SearchResult> search(float[] queryVector, int topK) throws Exception {
    ObjectMapper mapper = HopJson.newMapper();
    ObjectNode body = mapper.createObjectNode();
    ArrayNode vectorNode = body.putArray("vector");
    for (float v : queryVector) {
      vectorNode.add(v);
    }
    body.put("limit", topK);
    body.put("with_payload", true);

    HttpResponse<byte[]> response =
        sendRequest(
            "POST",
            "/collections/" + collectionName + "/points/search",
            mapper.writeValueAsBytes(body));

    JsonNode root = mapper.readTree(new String(response.body(), StandardCharsets.UTF_8));
    JsonNode resultArray = root.path("result");

    List<SearchResult> results = new ArrayList<>();
    if (resultArray.isArray()) {
      for (JsonNode item : resultArray) {
        float score = (float) item.path("score").asDouble(0);
        JsonNode payload = item.path("payload");
        String filePath = payload.path("filePath").asText("");
        String section = payload.path("section").asText("");
        String content = payload.path("content").asText("");
        results.add(new SearchResult(filePath, section, content, score));
      }
    }

    log.logDetailed("Qdrant search returned " + results.size() + " results");
    return results;
  }

  @Override
  public void deleteAll() throws Exception {
    if (collectionExists()) {
      sendRequest("DELETE", "/collections/" + collectionName, null);
      log.logBasic("Deleted Qdrant collection '" + collectionName + "'");
    }
  }

  @Override
  public long count() throws Exception {
    if (!collectionExists()) {
      return 0;
    }
    HttpResponse<byte[]> response = sendRequest("GET", "/collections/" + collectionName, null);
    ObjectMapper mapper = HopJson.newMapper();
    JsonNode root = mapper.readTree(new String(response.body(), StandardCharsets.UTF_8));
    return root.path("result").path("points_count").asLong(0);
  }

  @Override
  public boolean collectionExists() throws Exception {
    try {
      HttpResponse<byte[]> response = sendRequest("GET", "/collections/" + collectionName, null);
      return response.statusCode() == 200;
    } catch (IllegalStateException e) {
      return false;
    }
  }

  @Override
  public boolean isReachable() {
    try {
      sendRequest("GET", "/collections", null);
      return true;
    } catch (Exception e) {
      log.logDetailed("Qdrant is not reachable: " + e.getMessage());
      return false;
    }
  }

  private HttpResponse<byte[]> sendRequest(String method, String path, byte[] body)
      throws Exception {
    String url = qdrantUrl + path;

    HttpRequest.Builder builder =
        HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofSeconds(30))
            .header("Content-Type", "application/json");

    if (body != null) {
      builder.method(method, HttpRequest.BodyPublishers.ofByteArray(body));
    } else {
      builder.method(method, HttpRequest.BodyPublishers.noBody());
    }

    HttpResponse<byte[]> response =
        httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofByteArray());

    int status = response.statusCode();
    if (status >= 400) {
      String errorBody =
          response.body() == null ? "" : new String(response.body(), StandardCharsets.UTF_8);
      log.logError("Qdrant API error: " + method + " " + path + " -> " + status + ": " + errorBody);
      throw new IllegalStateException(
          "Qdrant API error (" + status + "): " + truncate(errorBody, 300));
    }

    return response;
  }

  private static String truncate(String value, int max) {
    if (value == null) {
      return "";
    }
    return value.length() <= max ? value : value.substring(0, max) + "...";
  }
}
