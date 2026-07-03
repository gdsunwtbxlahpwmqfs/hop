# Pipeline: 0006-groupby-concat

## Basic Information

- **Pipeline Name:** 0006-groupby-concat
- **Source File:** `03-转换插件/统计与分组类/samples/0006-groupby-concat.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Group by | GroupBy |
| Memory group by | MemoryGroupBy |
| Sort rows | SortRows |
| Sort rows result | SortRows |
| Merge Streams | StreamSchema |

## Hops

| From | To |
|------|----|
| Data grid | Memory group by |
| Data grid | Sort rows |
| Sort rows | Group by |
| Group by | Merge Streams |
| Merge Streams | Sort rows result |
| Memory group by | Merge Streams |
