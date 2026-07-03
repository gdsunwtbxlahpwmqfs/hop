# Pipeline: 0077-merge-rows

## Basic Information

- **Pipeline Name:** 0077-merge-rows
- **Source File:** `03-转换插件/查找与连接类/samples/0077-merge-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| Merge rows (diff) | MergeRows |
| source1 | DataGrid |
| source2 | DataGrid |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| source1 | Merge rows (diff) |
| source2 | Merge rows (diff) |
| Merge rows (diff) | validate |
