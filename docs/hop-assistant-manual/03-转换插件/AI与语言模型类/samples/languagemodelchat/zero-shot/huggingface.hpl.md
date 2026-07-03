# Pipeline: languagemodelchat-huggingface

## Basic Information

- **Pipeline Name:** languagemodelchat-huggingface
- **Source File:** `03-转换插件/AI与语言模型类/samples/languagemodelchat/zero-shot/huggingface.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| HF_TOKEN | hf_abc123 |  |

## Transforms

| Name | Type |
|------|------|
| Llama-3.1-70B-Instruct | LanguageModelChat |
| Text output | TextFileOutput |
| messages | DataGrid |
| prompt | EnhancedJsonOutput |

## Hops

| From | To |
|------|----|
| Llama-3.1-70B-Instruct | Text output |
| messages | prompt |
| prompt | Llama-3.1-70B-Instruct |
