# Pipeline: languagemodelchat-mistral

## Basic Information

- **Pipeline Name:** languagemodelchat-mistral
- **Source File:** `03-转换插件/AI与语言模型类/samples/languagemodelchat/zero-shot/mistral.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| MISTRAL_API_KEY | abc123 |  |

## Transforms

| Name | Type |
|------|------|
| Text output | TextFileOutput |
| messages | DataGrid |
| mistral-large-latest | LanguageModelChat |
| prompt | EnhancedJsonOutput |

## Hops

| From | To |
|------|----|
| messages | prompt |
| prompt | mistral-large-latest |
| mistral-large-latest | Text output |
