# Pipeline: 0005-metastructure-options

## Basic Information

- **Pipeline Name:** 0005-metastructure-options
- **Source File:** `03-转换插件/日志与监控类/samples/0005-metastructure-options.hpl`

## Transforms

| Name | Type |
|------|------|
| Metadata structure of stream | TransformMetaStructure |
| Test data | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Metadata structure of stream | Verify |
| Test data | Metadata structure of stream |
