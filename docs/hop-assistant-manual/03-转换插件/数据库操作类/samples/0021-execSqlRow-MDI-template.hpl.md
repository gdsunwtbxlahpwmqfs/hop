# Pipeline: 0021-execSqlRow-MDI-template

## Basic Information

- **Pipeline Name:** 0021-execSqlRow-MDI-template
- **Source File:** `03-转换插件/数据库操作类/samples/0021-execSqlRow-MDI-template.hpl`

## Transforms

| Name | Type |
|------|------|
| Execute row SQL script | ExecSqlRow |
| check if fields exist | SelectValues |
| sql lines | DataGrid |

## Hops

| From | To |
|------|----|
| sql lines | Execute row SQL script |
| Execute row SQL script | check if fields exist |
