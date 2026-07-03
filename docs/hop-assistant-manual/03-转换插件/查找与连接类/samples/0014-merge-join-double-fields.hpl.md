# Pipeline: 0014-merge-join-double-fields

## Basic Information

- **Pipeline Name:** 0014-merge-join-double-fields
- **Source File:** `03-转换插件/查找与连接类/samples/0014-merge-join-double-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| A | DataGrid |
| B | DataGrid |
| Inner | MergeJoin |
| Verify Inner | Dummy |

## Hops

| From | To |
|------|----|
| A | Inner |
| B | Inner |
| Inner | Verify Inner |
