# Pipeline: 0001-sort-order-ascending-case-sensitive

## Basic Information

- **Pipeline Name:** 0001-sort-order-ascending-case-sensitive
- **Source File:** `03-转换插件/统计与分组类/samples/0001-sort-order-ascending-case-sensitive.hpl`

## Transforms

| Name | Type |
|------|------|
| Sort rows | SortRows |
| Test strings | DataGrid |
| concat values | GroupBy |
| SORT_RESULT | SetVariable |

## Hops

| From | To |
|------|----|
| Test strings | Sort rows |
| Sort rows | concat values |
| concat values | SORT_RESULT |
