# Pipeline: 0102-sorted-merge-multi-copy

## Basic Information

- **Pipeline Name:** 0102-sorted-merge-multi-copy
- **Description:** Regression for issue #7219: Sort Rows (many copies) with few rows into Sorted Merge.
- **Source File:** `03-转换插件/统计与分组类/samples/0102-sorted-merge-multi-copy.hpl`

## Transforms

| Name | Type |
|------|------|
| input | DataGrid |
| Sort rows | SortRows |
| Sorted merge | SortedMerge |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| input | Sort rows |
| Sort rows | Sorted merge |
| Sorted merge | validate |
