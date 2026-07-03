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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.hop.core.logging.LogChannel;

/**
 * Scans the docs directory, splits each Markdown file into chunks, generates embeddings in batches,
 * and upserts them into the vector store.
 */
public class DocumentIndexer {

  private static final LogChannel log = new LogChannel("DocumentIndexer");

  private static final int BATCH_SIZE = 20;

  private final DocumentSplitter splitter;
  private final EmbeddingClient embeddingClient;
  private final VectorStore vectorStore;

  public DocumentIndexer(
      DocumentSplitter splitter, EmbeddingClient embeddingClient, VectorStore vectorStore) {
    this.splitter = splitter;
    this.embeddingClient = embeddingClient;
    this.vectorStore = vectorStore;
  }

  /**
   * Builds (or rebuilds) the full index from the docs directory.
   *
   * @param docsPath the path to the Markdown docs directory
   * @return the total number of chunks indexed
   * @throws Exception if indexing fails
   */
  public int index(String docsPath) throws Exception {
    log.logBasic("Starting full index build from: " + docsPath);

    // Step 1: Ensure collection exists with correct vector dimension
    int vectorSize = embeddingClient.getEmbeddingDimension();
    log.logBasic("Embedding dimension: " + vectorSize);
    vectorStore.ensureCollection(vectorSize);

    // Step 2: Scan and split all Markdown files
    List<KnowledgeChunk> allChunks = scanAndSplit(docsPath);
    log.logBasic("Total chunks generated: " + allChunks.size());

    // Step 3: Batch embed and upsert
    int totalIndexed = 0;
    for (int i = 0; i < allChunks.size(); i += BATCH_SIZE) {
      int end = Math.min(i + BATCH_SIZE, allChunks.size());
      List<KnowledgeChunk> batch = allChunks.subList(i, end);

      List<String> texts = new ArrayList<>(batch.size());
      for (KnowledgeChunk chunk : batch) {
        texts.add(chunk.content());
      }

      List<float[]> vectors = embeddingClient.embedBatch(texts);
      vectorStore.upsert(batch, vectors);

      totalIndexed += batch.size();
      log.logDetailed(
          "Indexed batch " + ((i / BATCH_SIZE) + 1) + ": " + totalIndexed + "/" + allChunks.size());
    }

    log.logBasic("Index build complete: " + totalIndexed + " chunks");
    return totalIndexed;
  }

  /** Scans the docs directory for .md files and splits each into chunks. */
  private List<KnowledgeChunk> scanAndSplit(String docsPath) throws IOException {
    List<KnowledgeChunk> allChunks = new ArrayList<>();
    Path root = Paths.get(docsPath);

    if (!Files.exists(root)) {
      throw new IOException("Docs directory does not exist: " + docsPath);
    }

    try (Stream<Path> paths = Files.walk(root, FileVisitOption.FOLLOW_LINKS)) {
      List<Path> mdFiles =
          paths
              .filter(Files::isRegularFile)
              .filter(p -> p.toString().endsWith(".md"))
              .sorted()
              .toList();

      log.logBasic("Found " + mdFiles.size() + " Markdown files");

      for (Path mdFile : mdFiles) {
        String relativePath = root.relativize(mdFile).toString().replace('\\', '/');
        String content = Files.readString(mdFile, StandardCharsets.UTF_8);
        List<KnowledgeChunk> chunks = splitter.split(relativePath, content);
        allChunks.addAll(chunks);
      }
    }

    return allChunks;
  }
}
