# Pipeline: 0022-execSqlRow-test

## Basic Information

- **Pipeline Name:** 0022-execSqlRow-test
- **Source File:** `03-转换插件/数据库操作类/samples/0022-execSqlRow-test.hpl`

## Transforms

| Name | Type |
|------|------|
| Execute row SQL script | ExecSqlRow |
| Verify | Dummy |
| sql lines | DataGrid |

## Hops

| From | To |
|------|----|
| sql lines | Execute row SQL script |
| Execute row SQL script | Verify |
