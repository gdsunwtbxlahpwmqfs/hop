# Pipeline: 0020-table-output-insert-multiple-copies

## Basic Information

- **Pipeline Name:** 0020-table-output-insert-multiple-copies
- **Source File:** `03-转换插件/数据库操作类/samples/0020-table-output-insert-multiple-copies.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate 10000 rows | RowGenerator |
| Insert table_output | TableOutput |

## Hops

| From | To |
|------|----|
| Generate 10000 rows | Insert table_output |
