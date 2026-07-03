# Pipeline: 0033-table-output-drop-columns

## Basic Information

- **Pipeline Name:** 0033-table-output-drop-columns
- **Description:** Test drop surplus columns functionality
- **Source File:** `03-转换插件/数据库操作类/samples/0033-table-output-drop-columns.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate data with 2 columns | RowGenerator |
| Drop columns table output | TableOutput |

## Hops

| From | To |
|------|----|
| Generate data with 2 columns | Drop columns table output |
