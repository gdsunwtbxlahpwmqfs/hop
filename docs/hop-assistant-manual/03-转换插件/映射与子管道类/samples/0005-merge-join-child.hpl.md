# Pipeline: 0005-merge-join-child

## Basic Information

- **Pipeline Name:** 0005-merge-join-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0005-merge-join-child.hpl`

## Transforms

| Name | Type |
|------|------|
| A | DataGrid |
| B | DataGrid |
| Inner | MergeJoin |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| A | Inner |
| Inner | Output |
| B | Inner |
