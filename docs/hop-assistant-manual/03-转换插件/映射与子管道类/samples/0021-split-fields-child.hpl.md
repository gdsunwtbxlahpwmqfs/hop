# Pipeline: 0021-split-fields-child

## Basic Information

- **Pipeline Name:** 0021-split-fields-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0021-split-fields-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Split value | FieldSplitter |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| sample data | Split value |
| Split value | Output |
