# Pipeline: 0033-table-output-drop-recreate

## Basic Information

- **Pipeline Name:** 0033-table-output-drop-recreate
- **Description:** Test drop and recreate table functionality
- **Source File:** `03-转换插件/数据库操作类/samples/0033-table-output-drop-recreate.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate test data | RowGenerator |
| Drop and recreate table output | TableOutput |

## Hops

| From | To |
|------|----|
| Generate test data | Drop and recreate table output |
