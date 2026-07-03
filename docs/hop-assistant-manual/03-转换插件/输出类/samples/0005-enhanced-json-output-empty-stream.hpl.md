# Pipeline: 0005-enhanced-json-output-empty-stream

## Basic Information

- **Pipeline Name:** 0005-enhanced-json-output-empty-stream
- **Source File:** `03-转换插件/输出类/samples/0005-enhanced-json-output-empty-stream.hpl`

## Transforms

| Name | Type |
|------|------|
| Enhanced JSON Output | EnhancedJsonOutput |
| Generate rows | RowGenerator |

## Hops

| From | To |
|------|----|
| Generate rows | Enhanced JSON Output |
