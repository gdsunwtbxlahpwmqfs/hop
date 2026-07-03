# Pipeline: 0090-sort-rows

## Basic Information

- **Pipeline Name:** 0090-sort-rows
- **Source File:** `03-转换插件/统计与分组类/samples/0090-sort-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| name ASC i | SortRows |
| name ASC s | SortRows |
| name DESC i | SortRows |
| name DESC s | SortRows |
| sample-data | DataGrid |
| validate ASC i | Dummy |
| validate ASC s | Dummy |
| validate DESC i | Dummy |
| validate DESC s | Dummy |

## Hops

| From | To |
|------|----|
| sample-data | name ASC i |
| sample-data | name ASC s |
| name ASC i | validate ASC i |
| name ASC s | validate ASC s |
| name DESC i | validate DESC i |
| name DESC s | validate DESC s |
| sample-data | name DESC i |
| sample-data | name DESC s |
