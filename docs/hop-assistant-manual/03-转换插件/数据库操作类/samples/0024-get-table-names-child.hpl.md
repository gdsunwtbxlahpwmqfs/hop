# Pipeline: 0024-get-table-names-child

## Basic Information

- **Pipeline Name:** 0024-get-table-names-child
- **Source File:** `03-转换插件/数据库操作类/samples/0024-get-table-names-child.hpl`

## Transforms

| Name | Type |
|------|------|
| 0024 tables | FilterRows |
| Get table names | GetTableNames |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| 0024 tables | Output |
| Get table names | 0024 tables |
