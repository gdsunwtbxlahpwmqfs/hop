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

import java.util.List;

/**
 * Abstraction over a vector database for storing and retrieving document chunks. Implementations
 * include {@link QdrantVectorStore}.
 */
public interface VectorStore {

  /** Creates the collection if it does not exist, with the given vector dimension. */
  void ensureCollection(int vectorSize) throws Exception;

  /** Inserts or updates chunks with their corresponding embedding vectors. */
  void upsert(List<KnowledgeChunk> chunks, List<float[]> vectors) throws Exception;

  /** Searches for the top-K most similar chunks to the given query vector. */
  List<SearchResult> search(float[] queryVector, int topK) throws Exception;

  /** Deletes all points in the collection. */
  void deleteAll() throws Exception;

  /** Returns the number of points in the collection. */
  long count() throws Exception;

  /** Returns whether the collection exists. */
  boolean collectionExists() throws Exception;

  /** Checks if the vector store service is reachable. */
  boolean isReachable();
}
