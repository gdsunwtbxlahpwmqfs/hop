# Pipeline: languagemodelchat-anthropic

## Basic Information

- **Pipeline Name:** languagemodelchat-anthropic
- **Source File:** `03-转换插件/AI与语言模型类/samples/languagemodelchat/zero-shot/anthropic.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| ANTHROPIC_API_KEY | sk-ant-api03-abc123 |  |

## Transforms

| Name | Type |
|------|------|
| Text output | TextFileOutput |
| claude-3-5-sonnet | LanguageModelChat |
| messages | DataGrid |
| prompt | EnhancedJsonOutput |

## Hops

| From | To |
|------|----|
| messages | prompt |
| prompt | claude-3-5-sonnet |
| claude-3-5-sonnet | Text output |
