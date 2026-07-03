# Pipeline: 0033-table-output-add-columns

## Basic Information

- **Pipeline Name:** 0033-table-output-add-columns
- **Description:** Test add missing columns functionality
- **Source File:** `03-转换插件/数据库操作类/samples/0033-table-output-add-columns.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate data with 4 columns | RowGenerator |
| Add columns table output | TableOutput |

## Hops

| From | To |
|------|----|
| Generate data with 4 columns | Add columns table output |
