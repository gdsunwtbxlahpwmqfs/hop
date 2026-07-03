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

import java.util.ArrayList;
import java.util.List;
import org.apache.hop.core.logging.LogChannel;
import org.apache.hop.ui.hopgui.assistant.LlmAssistantConfig;

/**
 * Facade that ties together embedding, vector store, indexing, and retrieval. This is the main
 * entry point for both the REST API and the LLM assistant dialog.
 *
 * <p>All methods degrade gracefully: if RAG is disabled or the vector store is unreachable, search
 * returns an empty list and indexing throws a descriptive exception.
 */
public class KnowledgeBaseService {

  private static final LogChannel log = new LogChannel("KnowledgeBaseService");

  private static final float MIN_SCORE_THRESHOLD = 0.3f;

  private static KnowledgeBaseService instance;

  private final KnowledgeBaseConfig kbConfig;
  private final EmbeddingClient embeddingClient;
  private final VectorStore vectorStore;
  private final DocumentIndexer indexer;

  private KnowledgeBaseService() {
    this.kbConfig = KnowledgeBaseConfig.getInstance();
    LlmAssistantConfig llmConfig = LlmAssistantConfig.getInstance();
    this.embeddingClient = new EmbeddingClient(llmConfig, kbConfig);
    this.vectorStore = new QdrantVectorStore(kbConfig);
    this.indexer =
        new DocumentIndexer(
            new DocumentSplitter(kbConfig.getChunkSize(), kbConfig.getChunkOverlap()),
            embeddingClient,
            vectorStore);
  }

  /** Returns the singleton service instance. */
  public static synchronized KnowledgeBaseService getInstance() {
    if (instance == null) {
      instance = new KnowledgeBaseService();
    }
    return instance;
  }

  /** Returns whether RAG is enabled by configuration. */
  public boolean isEnabled() {
    return kbConfig.isEnabled();
  }

  /**
   * Searches the knowledge base for chunks relevant to the query, using the configured default
   * topK.
   *
   * @param query the user question
   * @return a list of relevant search results, filtered by score threshold
   */
  public List<SearchResult> search(String query) {
    return search(query, kbConfig.getTopK());
  }

  /**
   * Searches the knowledge base for chunks relevant to the query.
   *
   * @param query the user question
   * @param topK maximum number of results to return
   * @return a list of relevant search results, filtered by score threshold
   */
  public List<SearchResult> search(String query, int topK) {
    if (!kbConfig.isEnabled()) {
      return List.of();
    }
    try {
      if (!vectorStore.collectionExists()) {
        log.logBasic(
            "Collection '"
                + kbConfig.getQdrantCollection()
                + "' not found - please build the index first");
        return List.of();
      }
      float[] queryVector = embeddingClient.embed(query);
      List<SearchResult> raw = vectorStore.search(queryVector, topK);

      // Filter low-score results
      List<SearchResult> filtered = new ArrayList<>();
      for (SearchResult result : raw) {
        if (result.score() >= MIN_SCORE_THRESHOLD) {
          filtered.add(result);
        }
      }

      log.logDetailed(
          "KB search for '"
              + query
              + "': "
              + filtered.size()
              + " results (filtered from "
              + raw.size()
              + ")");
      return filtered;
    } catch (Exception e) {
      log.logError("KB search failed, degrading to empty results: " + e.getMessage());
      return List.of();
    }
  }

  /**
   * Searches and formats the results as a context string suitable for injection into an LLM system
   * prompt.
   *
   * @param query the user question
   * @return formatted context text, or empty string if no results
   */
  public String buildContextPrompt(String query) {
    List<SearchResult> results = search(query);
    if (results.isEmpty()) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("\n\n以下是与用户问题相关的参考文档，请基于这些文档回答用户的问题：\n\n");
    for (int i = 0; i < results.size(); i++) {
      SearchResult result = results.get(i);
      sb.append("[").append(i + 1).append("] 来源: ").append(result.filePath());
      if (result.section() != null && !result.section().isEmpty()) {
        sb.append(" > ").append(result.section());
      }
      sb.append("\n").append(result.content()).append("\n\n");
    }
    return sb.toString();
  }

  /**
   * Builds the full index from the docs directory.
   *
   * @return index status with chunk count
   * @throws Exception if indexing fails
   */
  public IndexStatus buildIndex() throws Exception {
    long startTime = System.currentTimeMillis();
    int chunks = indexer.index(kbConfig.getDocsPath());
    long duration = (System.currentTimeMillis() - startTime) / 1000;
    return new IndexStatus(true, true, chunks, "Indexed in " + duration + "s");
  }

  /** Returns the current index status. */
  public IndexStatus getStatus() {
    if (!kbConfig.isEnabled()) {
      return new IndexStatus(false, false, 0, "RAG is disabled");
    }
    try {
      if (!vectorStore.isReachable()) {
        return new IndexStatus(true, false, 0, "Qdrant service not reachable");
      }
      long count = vectorStore.count();
      return new IndexStatus(true, count > 0, count, count > 0 ? "Ready" : "Not indexed");
    } catch (Exception e) {
      return new IndexStatus(true, false, 0, "Vector store unavailable: " + e.getMessage());
    }
  }

  /** Clears the index. */
  public void clear() throws Exception {
    vectorStore.deleteAll();
    log.logBasic("Knowledge base index cleared");
  }
}
