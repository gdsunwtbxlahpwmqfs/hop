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

import org.apache.commons.lang3.StringUtils;

/**
 * Configuration for the knowledge base / RAG subsystem, sourced from environment variables (and
 * matching system properties as a fallback), following the same pattern as {@code
 * LlmAssistantConfig}.
 *
 * <p>Supported variables:
 *
 * <ul>
 *   <li><strong>HOP_KB_ENABLED</strong> &ndash; {@code true}/{@code false} to enable RAG (default:
 *       {@code false}).
 *   <li><strong>HOP_KB_QDRANT_URL</strong> &ndash; Qdrant server URL (default: {@code
 *       http://localhost:6333}).
 *   <li><strong>HOP_KB_QDRANT_COLLECTION</strong> &ndash; Qdrant collection name (default: {@code
 *       hop_kb}).
 *   <li><strong>HOP_KB_EMBEDDING_MODEL</strong> &ndash; Embedding model id for LiteLLM (default:
 *       {@code text-embedding-3-small}).
 *   <li><strong>HOP_KB_DOCS_PATH</strong> &ndash; Path to the Markdown docs directory (default:
 *       {@code docs/hop-assistant-manual}).
 *   <li><strong>HOP_KB_CHUNK_SIZE</strong> &ndash; Max characters per chunk (default: {@code 800}).
 *   <li><strong>HOP_KB_CHUNK_OVERLAP</strong> &ndash; Overlap characters between chunks (default:
 *       {@code 150}).
 *   <li><strong>HOP_KB_TOP_K</strong> &ndash; Number of chunks to retrieve (default: {@code 5}).
 * </ul>
 *
 * <p>The embedding endpoint and API key are reused from the LLM assistant configuration ({@code
 * HOP_LLM_API_URL} and {@code HOP_LLM_API_KEY}).
 */
public final class KnowledgeBaseConfig {

  public static final String ENV_ENABLED = "HOP_KB_ENABLED";
  public static final String ENV_QDRANT_URL = "HOP_KB_QDRANT_URL";
  public static final String ENV_QDRANT_COLLECTION = "HOP_KB_QDRANT_COLLECTION";
  public static final String ENV_EMBEDDING_MODEL = "HOP_KB_EMBEDDING_MODEL";
  public static final String ENV_DOCS_PATH = "HOP_KB_DOCS_PATH";
  public static final String ENV_CHUNK_SIZE = "HOP_KB_CHUNK_SIZE";
  public static final String ENV_CHUNK_OVERLAP = "HOP_KB_CHUNK_OVERLAP";
  public static final String ENV_TOP_K = "HOP_KB_TOP_K";

  private final boolean enabled;
  private final String qdrantUrl;
  private final String qdrantCollection;
  private final String embeddingModel;
  private final String docsPath;
  private final int chunkSize;
  private final int chunkOverlap;
  private final int topK;

  private static KnowledgeBaseConfig instance;

  private KnowledgeBaseConfig() {
    this.enabled = Boolean.parseBoolean(resolve(ENV_ENABLED, "false"));
    this.qdrantUrl = resolve(ENV_QDRANT_URL, "http://localhost:6333");
    this.qdrantCollection = resolve(ENV_QDRANT_COLLECTION, "qi_hop_kb");
    this.embeddingModel = resolve(ENV_EMBEDDING_MODEL, "text-embedding-3-small");
    this.docsPath = resolve(ENV_DOCS_PATH, "docs/hop-assistant-manual");
    this.chunkSize = parseInt(resolve(ENV_CHUNK_SIZE, "800"), 800);
    this.chunkOverlap = parseInt(resolve(ENV_CHUNK_OVERLAP, "150"), 150);
    this.topK = parseInt(resolve(ENV_TOP_K, "5"), 5);
  }

  /** Returns the singleton configuration instance. */
  public static synchronized KnowledgeBaseConfig getInstance() {
    if (instance == null) {
      instance = new KnowledgeBaseConfig();
    }
    return instance;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public String getQdrantUrl() {
    return qdrantUrl;
  }

  public String getQdrantCollection() {
    return qdrantCollection;
  }

  public String getEmbeddingModel() {
    return embeddingModel;
  }

  public String getDocsPath() {
    return docsPath;
  }

  public int getChunkSize() {
    return chunkSize;
  }

  public int getChunkOverlap() {
    return chunkOverlap;
  }

  public int getTopK() {
    return topK;
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
