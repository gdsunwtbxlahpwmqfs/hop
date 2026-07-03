# Pipeline: languagemodelchat-ollama

## Basic Information

- **Pipeline Name:** languagemodelchat-ollama
- **Source File:** `03-转换插件/AI与语言模型类/samples/languagemodelchat/zero-shot/ollama.hpl`

## Transforms

| Name | Type |
|------|------|
| Text output | TextFileOutput |
| ollama:phi3 | LanguageModelChat |
| prompt | DataGrid |

## Hops

| From | To |
|------|----|
| prompt | ollama:phi3 |
| ollama:phi3 | Text output |
