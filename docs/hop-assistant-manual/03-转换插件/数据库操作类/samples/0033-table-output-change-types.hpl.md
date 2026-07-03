# Pipeline: 0033-table-output-change-types

## Basic Information

- **Pipeline Name:** 0033-table-output-change-types
- **Description:** Test change column data types functionality
- **Source File:** `03-转换插件/数据库操作类/samples/0033-table-output-change-types.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate data with new types | RowGenerator |
| Change types table output | TableOutput |

## Hops

| From | To |
|------|----|
| Generate data with new types | Change types table output |
