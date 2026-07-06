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

package org.apache.hop.ui.hopgui.perspective.explorer.config;

public class ExplorerPerspectiveConfig {

  public static final String HOP_CONFIG_EXPLORER_PERSPECTIVE_CONFIG_KEY = "explorer-perspective";

  private String lazyLoadingDepth;
  private String fileLoadingMaxSize;
  private Boolean fileExplorerVisibleByDefault;
  private Boolean openingHelpFiles;
  private String rootFolder;
  private String rootName;

  public ExplorerPerspectiveConfig() {
    this.lazyLoadingDepth = "0";
    this.fileLoadingMaxSize = "16";
    this.fileExplorerVisibleByDefault = true;
    this.openingHelpFiles = false;
    this.rootFolder = null;
    this.rootName = null;
  }

  public ExplorerPerspectiveConfig(ExplorerPerspectiveConfig config) {
    this();
    this.lazyLoadingDepth = config.lazyLoadingDepth;
    this.fileLoadingMaxSize = config.fileLoadingMaxSize;
    this.fileExplorerVisibleByDefault = config.fileExplorerVisibleByDefault;
    this.openingHelpFiles = config.openingHelpFiles;
    this.rootFolder = config.rootFolder;
    this.rootName = config.rootName;
  }

  public String getLazyLoadingDepth() {
    return lazyLoadingDepth;
  }

  public void setLazyLoadingDepth(String lazyLoadingDepth) {
    this.lazyLoadingDepth = lazyLoadingDepth;
  }

  public String getFileLoadingMaxSize() {
    return fileLoadingMaxSize;
  }

  public void setFileLoadingMaxSize(String fileLoadingMaxSize) {
    this.fileLoadingMaxSize = fileLoadingMaxSize;
  }

  public Boolean getFileExplorerVisibleByDefault() {
    return fileExplorerVisibleByDefault;
  }

  public void setFileExplorerVisibleByDefault(Boolean fileExplorerVisibleByDefault) {
    this.fileExplorerVisibleByDefault = fileExplorerVisibleByDefault;
  }

  public Boolean isOpeningHelpFiles() {
    return openingHelpFiles != null ? openingHelpFiles : false;
  }

  public void setOpeningHelpFiles(Boolean openingHelpFiles) {
    this.openingHelpFiles = openingHelpFiles;
  }

  public String getRootFolder() {
    return rootFolder;
  }

  public void setRootFolder(String rootFolder) {
    this.rootFolder = rootFolder;
  }

  public String getRootName() {
    return rootName;
  }

  public void setRootName(String rootName) {
    this.rootName = rootName;
  }
}
