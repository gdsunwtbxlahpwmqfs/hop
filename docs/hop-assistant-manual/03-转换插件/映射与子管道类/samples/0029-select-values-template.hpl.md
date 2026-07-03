# Pipeline: 0029-select-values-template

## Basic Information

- **Pipeline Name:** 0029-select-values-template
- **Source File:** `03-转换插件/映射与子管道类/samples/0029-select-values-template.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Metadata structure of stream | TransformMetaStructure |
| Select values | SelectValues |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Select values |
| Select values | Metadata structure of stream |
| Metadata structure of stream | Dummy (do nothing) |
