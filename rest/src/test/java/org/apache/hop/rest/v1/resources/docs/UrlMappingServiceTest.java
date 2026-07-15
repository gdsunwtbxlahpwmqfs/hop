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

package org.apache.hop.rest.v1.resources.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlMappingServiceTest {

  private UrlMappingService service;

  @BeforeEach
  void setUp() {
    service = UrlMappingService.getInstance();
    service.clear();
  }

  @Test
  void testLoadAndLookup() {
    service.load();
    UrlMapping mapping = service.lookup("/pipeline/transforms/csvinput.html");
    assertNotNull(mapping, "Should find CSV input mapping");
    assertEquals("matched", mapping.getStatus());
    assertNotNull(mapping.getMdRelativePath());
    assertTrue(mapping.getMdRelativePath().endsWith(".md"));
  }

  @Test
  void testLookupWithLeadingSlash() {
    service.load();
    UrlMapping m1 = service.lookup("/pipeline/transforms/csvinput.html");
    UrlMapping m2 = service.lookup("pipeline/transforms/csvinput.html");
    assertNotNull(m1);
    assertNotNull(m2);
    assertEquals(m1.getDocumentationUrl(), m2.getDocumentationUrl());
  }

  @Test
  void testLookupNotFound() {
    service.load();
    UrlMapping mapping = service.lookup("/nonexistent/plugin.html");
    assertNull(mapping, "Should return null for unknown URL");
  }

  @Test
  void testLookupNull() {
    service.load();
    assertNull(service.lookup(null));
    assertNull(service.lookup(""));
    assertNull(service.lookup("   "));
  }

  @Test
  void testGetAllMappings() {
    service.load();
    List<UrlMapping> all = service.getAllMappings();
    assertFalse(all.isEmpty(), "Should have mappings loaded");
    assertTrue(all.size() > 100, "Should have hundreds of mappings");
  }

  @Test
  void testGetStats() {
    service.load();
    Map<String, Object> stats = service.getStats();
    assertNotNull(stats);
    assertTrue((long) stats.get("total") > 0, "Total should be positive");
    assertTrue((long) stats.get("matched") > 0, "Matched should be positive");
    assertNotNull(stats.get("matchRate"));
  }

  @Test
  void testUnmatchedEntry() {
    service.load();
    List<UrlMapping> unmatched =
        service.getAllMappings().stream().filter(m -> "unmatched".equals(m.getStatus())).toList();
    if (unmatched.isEmpty()) {
      // All entries are matched - this is acceptable
      return;
    }
    UrlMapping first = unmatched.get(0);
    assertNotNull(first.getNote(), "Unmatched entries should have a note");
    assertTrue(first.getNote().contains("TODO"));
  }

  @Test
  void testDatabasePluginMapping() {
    service.load();
    UrlMapping mapping = service.lookup("/database/databases/postgresql.html");
    assertNotNull(mapping, "Should find PostgreSQL mapping");
    assertEquals("matched", mapping.getStatus());
    assertTrue(mapping.getMdRelativePath().toLowerCase().contains("postgresql"));
  }

  @Test
  void testMetadataMapping() {
    service.load();
    UrlMapping mapping = service.lookup("/metadata-types/mongodb-connection.html");
    assertNotNull(mapping, "Should find MongoDB connection mapping");
    assertEquals("matched", mapping.getStatus());
  }

  @Test
  void testReload() {
    service.load();
    long size1 = service.getAllMappings().size();
    service.reload();
    long size2 = service.getAllMappings().size();
    assertEquals(size1, size2, "Reload should produce same number of mappings");
  }
}
