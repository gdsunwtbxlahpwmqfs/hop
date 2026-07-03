# Pipeline: 0035-get-variables

## Basic Information

- **Pipeline Name:** 0035-get-variables
- **Source File:** `03-转换插件/输入类/samples/0035-get-variables.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| A | aa | length 2 |
| B |  | length 0 |
| C | ccccc | length 5 |

## Transforms

| Name | Type |
|------|------|
| 1 row | RowGenerator |
| Get variables | GetVariable |
| Metadata structure of stream | TransformMetaStructure |
| Result | Dummy |
| Results2 | Dummy |

## Hops

| From | To |
|------|----|
| 1 row | Get variables |
| Get variables | Metadata structure of stream |
| Metadata structure of stream | Result |
| Get variables | Results2 |
