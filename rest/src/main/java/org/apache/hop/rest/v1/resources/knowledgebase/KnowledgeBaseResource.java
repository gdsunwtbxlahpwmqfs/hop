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

package org.apache.hop.rest.v1.resources.knowledgebase;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import org.apache.hop.rest.v1.resources.BaseResource;
import org.apache.hop.ui.hopgui.assistant.knowledgebase.IndexStatus;
import org.apache.hop.ui.hopgui.assistant.knowledgebase.KnowledgeBaseService;
import org.apache.hop.ui.hopgui.assistant.knowledgebase.SearchResult;

/**
 * REST API for the knowledge base / RAG subsystem. Provides endpoints for searching the knowledge
 * base, building the index, querying status, and clearing the index.
 */
@Path("/knowledgebase")
public class KnowledgeBaseResource extends BaseResource {

  private final KnowledgeBaseService kbService = KnowledgeBaseService.getInstance();

  /** Searches the knowledge base for chunks relevant to the query. */
  @POST
  @Path("/search")
  @Produces(MediaType.APPLICATION_JSON)
  public Response search(SearchRequest request) {
    try {
      if (request == null || request.getQuery() == null || request.getQuery().isBlank()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity("Query is required")
            .type(MediaType.TEXT_PLAIN)
            .build();
      }

      List<SearchResult> results =
          request.getTopK() != null && request.getTopK() > 0
              ? kbService.search(request.getQuery(), request.getTopK())
              : kbService.search(request.getQuery());
      return Response.ok(Map.of("results", results)).build();
    } catch (Exception e) {
      return getServerError("Error searching knowledge base", e);
    }
  }

  /** Builds (or rebuilds) the full index from the docs directory. */
  @POST
  @Path("/index")
  @Produces(MediaType.APPLICATION_JSON)
  public Response buildIndex() {
    try {
      IndexStatus status = kbService.buildIndex();
      return Response.ok(status).build();
    } catch (Exception e) {
      return getServerError("Error building knowledge base index", e);
    }
  }

  /** Returns the current index status. */
  @GET
  @Path("/status")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStatus() {
    try {
      IndexStatus status = kbService.getStatus();
      return Response.ok(status).build();
    } catch (Exception e) {
      return getServerError("Error getting knowledge base status", e);
    }
  }

  /** Clears the entire index. */
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response clear() {
    try {
      kbService.clear();
      return Response.ok(Map.of("deleted", true)).build();
    } catch (Exception e) {
      return getServerError("Error clearing knowledge base", e);
    }
  }
}
