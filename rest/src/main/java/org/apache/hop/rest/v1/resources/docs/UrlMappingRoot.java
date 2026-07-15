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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/** Root object for deserializing url-mapping.json. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlMappingRoot {

  @JsonProperty("version")
  private String version;

  @JsonProperty("generatedAt")
  private String generatedAt;

  @JsonProperty("stats")
  private Map<String, Object> stats;

  @JsonProperty("mappings")
  private List<UrlMapping> mappings;

  public UrlMappingRoot() {}

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getGeneratedAt() {
    return generatedAt;
  }

  public void setGeneratedAt(String generatedAt) {
    this.generatedAt = generatedAt;
  }

  public Map<String, Object> getStats() {
    return stats;
  }

  public void setStats(Map<String, Object> stats) {
    this.stats = stats;
  }

  public List<UrlMapping> getMappings() {
    return mappings;
  }

  public void setMappings(List<UrlMapping> mappings) {
    this.mappings = mappings;
  }
}
