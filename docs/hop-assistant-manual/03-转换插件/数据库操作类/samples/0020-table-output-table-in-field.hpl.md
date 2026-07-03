# Pipeline: 0020-table-output-table-in-field

## Basic Information

- **Pipeline Name:** 0020-table-output-table-in-field
- **Source File:** `03-转换插件/数据库操作类/samples/0020-table-output-table-in-field.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate 0 rows | RowGenerator |
| Insert table_output | TableOutput |

## Hops

| From | To |
|------|----|
| Generate 0 rows | Insert table_output |
