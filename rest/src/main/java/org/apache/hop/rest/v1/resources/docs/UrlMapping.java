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

/** A single mapping entry from documentationUrl to a local MD file path. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlMapping {

  @JsonProperty("annotationType")
  private String annotationType;

  @JsonProperty("pluginId")
  private String pluginId;

  @JsonProperty("pluginName")
  private String pluginName;

  @JsonProperty("documentationUrl")
  private String documentationUrl;

  @JsonProperty("status")
  private String status;

  @JsonProperty("mdRelativePath")
  private String mdRelativePath;

  @JsonProperty("note")
  private String note;

  @JsonProperty("adocRelativePath")
  private String adocRelativePath;

  @JsonProperty("adocFullPath")
  private String adocFullPath;

  @JsonProperty("adocManual")
  private String adocManual;

  public UrlMapping() {}

  public String getAnnotationType() {
    return annotationType;
  }

  public void setAnnotationType(String annotationType) {
    this.annotationType = annotationType;
  }

  public String getPluginId() {
    return pluginId;
  }

  public void setPluginId(String pluginId) {
    this.pluginId = pluginId;
  }

  public String getPluginName() {
    return pluginName;
  }

  public void setPluginName(String pluginName) {
    this.pluginName = pluginName;
  }

  public String getDocumentationUrl() {
    return documentationUrl;
  }

  public void setDocumentationUrl(String documentationUrl) {
    this.documentationUrl = documentationUrl;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMdRelativePath() {
    return mdRelativePath;
  }

  public void setMdRelativePath(String mdRelativePath) {
    this.mdRelativePath = mdRelativePath;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getAdocRelativePath() {
    return adocRelativePath;
  }

  public void setAdocRelativePath(String adocRelativePath) {
    this.adocRelativePath = adocRelativePath;
  }

  public String getAdocFullPath() {
    return adocFullPath;
  }

  public void setAdocFullPath(String adocFullPath) {
    this.adocFullPath = adocFullPath;
  }

  public String getAdocManual() {
    return adocManual;
  }

  public void setAdocManual(String adocManual) {
    this.adocManual = adocManual;
  }
}
