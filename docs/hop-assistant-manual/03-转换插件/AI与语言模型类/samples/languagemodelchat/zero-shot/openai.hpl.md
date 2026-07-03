# Pipeline: languagemodelchat-openai

## Basic Information

- **Pipeline Name:** languagemodelchat-openai
- **Source File:** `03-转换插件/AI与语言模型类/samples/languagemodelchat/zero-shot/openai.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| OPENAI_API_KEY | sk-abc123 |  |

## Transforms

| Name | Type |
|------|------|
| Text output | TextFileOutput |
| gpt-4-turbo | LanguageModelChat |
| messages | DataGrid |
| prompt | EnhancedJsonOutput |

## Hops

| From | To |
|------|----|
| messages | prompt |
| gpt-4-turbo | Text output |
| prompt | gpt-4-turbo |

## Notes

Query a Wikipedia image and respond in Spanish with a description of it.

https://upload.wikimedia.org/wikipedia/commons/3/3d/Fesoj_-_Papilio_machaon_%28by%29.jpg

---
