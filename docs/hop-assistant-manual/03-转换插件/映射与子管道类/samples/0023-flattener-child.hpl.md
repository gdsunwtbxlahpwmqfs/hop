# Pipeline: 0023-flattener-child

## Basic Information

- **Pipeline Name:** 0023-flattener-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0023-flattener-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Flatten data | Flattener |
| Output | Dummy |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| sample data | Flatten data |
| Flatten data | Output |
