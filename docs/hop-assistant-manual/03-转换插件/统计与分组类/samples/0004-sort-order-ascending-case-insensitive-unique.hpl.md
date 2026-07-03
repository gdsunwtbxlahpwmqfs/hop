# Pipeline: 0004-sort-order-ascending-case-insensitive-unique

## Basic Information

- **Pipeline Name:** 0004-sort-order-ascending-case-insensitive-unique
- **Source File:** `03-转换插件/统计与分组类/samples/0004-sort-order-ascending-case-insensitive-unique.hpl`

## Transforms

| Name | Type |
|------|------|
| SORT_RESULT | SetVariable |
| Sort rows | SortRows |
| Test strings | DataGrid |
| concat values | GroupBy |

## Hops

| From | To |
|------|----|
| Test strings | Sort rows |
| Sort rows | concat values |
| concat values | SORT_RESULT |
