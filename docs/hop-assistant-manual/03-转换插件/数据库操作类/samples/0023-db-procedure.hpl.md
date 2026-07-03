# Pipeline: 0023-db-procedure

## Basic Information

- **Pipeline Name:** 0023-db-procedure
- **Source File:** `03-转换插件/数据库操作类/samples/0023-db-procedure.hpl`

## Transforms

| Name | Type |
|------|------|
| Call DB procedure | DBProc |
| Verify | Dummy |
| sample values | DataGrid |

## Hops

| From | To |
|------|----|
| sample values | Call DB procedure |
| Call DB procedure | Verify |
