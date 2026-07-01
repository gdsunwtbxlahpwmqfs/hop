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

import java.util.Map;

/**
 * A single chunk of a document after splitting, ready to be embedded and stored in the vector
 * database.
 *
 * @param id unique identifier (hash of file path + chunk index)
 * @param filePath relative path of the source file (e.g. "03-转换插件/输入类/表输入.md")
 * @param section the heading this chunk belongs to
 * @param chunkIndex sequential index within the source file
 * @param content the chunk text content
 * @param metadata additional metadata (category, subcategory, etc.)
 */
public record KnowledgeChunk(
    String id,
    String filePath,
    String section,
    int chunkIndex,
    String content,
    Map<String, String> metadata) {}
