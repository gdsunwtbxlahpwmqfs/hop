/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.pipeline.transforms.languagemodelchat.internals;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.hop.pipeline.transforms.languagemodelchat.internals.ui.i18nUtil.i18n;

public enum ModelType {
  OPEN_AI("OPEN_AI", "LanguageModelChatDialog.ModelType.OPEN_AI"),
  ANTHROPIC("ANTHROPIC", "LanguageModelChatDialog.ModelType.ANTHROPIC"),
  OLLAMA("OLLAMA", "LanguageModelChatDialog.ModelType.OLLAMA"),
  MISTRAL("MISTRAL", "LanguageModelChatDialog.ModelType.MISTRAL"),
  HUGGING_FACE("HUGGING_FACE", "LanguageModelChatDialog.ModelType.HUGGING_FACE");

  private String code;
  private String i18nKey;

  ModelType(String code, String i18nKey) {
    this.code = code;
    this.i18nKey = i18nKey;
  }

  public static ModelType typeFromDescription(String description) {
    for (ModelType type : values()) {
      if (equalsIgnoreCase(type.description(), description)) {
        return type;
      }
    }
    return OPEN_AI;
  }

  public static String[] modelTypeDescriptions() {
    ModelType[] types = ModelType.values();
    String[] descriptions = new String[types.length];
    for (int i = 0; i < types.length; i++) {
      descriptions[i] = types[i].description();
    }
    return descriptions;
  }

  public String code() {
    return code;
  }

  public String description() {
    return i18n(i18nKey);
  }
}
