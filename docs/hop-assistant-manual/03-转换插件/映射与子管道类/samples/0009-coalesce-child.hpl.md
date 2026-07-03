# Pipeline: 0009-coalesce-child

## Basic Information

- **Pipeline Name:** 0009-coalesce-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0009-coalesce-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Coalesce Fields | Coalesce |
| Input | DataGrid |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Coalesce Fields | Output |
| Input | Coalesce Fields |
