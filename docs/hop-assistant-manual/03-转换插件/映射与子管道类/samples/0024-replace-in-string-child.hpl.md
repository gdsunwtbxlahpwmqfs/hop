# Pipeline: 0024-replace-in-string-child

## Basic Information

- **Pipeline Name:** 0024-replace-in-string-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0024-replace-in-string-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Replace in string | ReplaceString |
| Sample data | DataGrid |

## Hops

| From | To |
|------|----|
| Replace in string | Output |
| Sample data | Replace in string |
