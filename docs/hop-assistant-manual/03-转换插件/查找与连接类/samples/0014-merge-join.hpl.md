# Pipeline: 0014-merge-join

## Basic Information

- **Pipeline Name:** 0014-merge-join
- **Source File:** `03-转换插件/查找与连接类/samples/0014-merge-join.hpl`

## Transforms

| Name | Type |
|------|------|
| A | DataGrid |
| B | DataGrid |
| Inner | MergeJoin |
| Verify Inner | Dummy |
| Left Outer | MergeJoin |
| Verify Left Outer | Dummy |
| Right Outer | MergeJoin |
| Verify Right Outer | Dummy |
| Full Outer | MergeJoin |
| Verify Full Outer | Dummy |

## Hops

| From | To |
|------|----|
| A | Inner |
| B | Inner |
| Inner | Verify Inner |
| A | Left Outer |
| B | Left Outer |
| Left Outer | Verify Left Outer |
| Right Outer | Verify Right Outer |
| A | Right Outer |
| B | Right Outer |
| Full Outer | Verify Full Outer |
| B | Full Outer |
| A | Full Outer |
