# Pipeline: 0033-table-output-auto-create

## Basic Information

- **Pipeline Name:** 0033-table-output-auto-create
- **Description:** Test auto-create table functionality
- **Source File:** `03-转换插件/数据库操作类/samples/0033-table-output-auto-create.hpl`

## Transforms

| Name | Type |
|------|------|
| Auto-create table output | TableOutput |
| Generate test data | RowGenerator |

## Hops

| From | To |
|------|----|
| Generate test data | Auto-create table output |
