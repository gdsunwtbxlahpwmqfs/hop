# Pipeline: 0068-enhanced-json-output-empty-stream

## Basic Information

- **Pipeline Name:** 0068-enhanced-json-output-empty-stream
- **Source File:** `03-转换插件/samples/0068-enhanced-json-output-empty-stream.hpl`

## Transforms

| Name | Type |
|------|------|
| Enhanced JSON output for empty stream | EnhancedJsonOutput |
| don't get any files | GetFileNames |

## Hops

| From | To |
|------|----|
| don't get any files | Enhanced JSON output for empty stream |
