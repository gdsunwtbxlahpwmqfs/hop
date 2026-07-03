# Pipeline: 0039-postgresql-bulkloader-boolean

## Basic Information

- **Pipeline Name:** 0039-postgresql-bulkloader-boolean
- **Source File:** `03-转换插件/数据库操作类/samples/0039-postgresql-bulkloader-boolean.hpl`

## Transforms

| Name | Type |
|------|------|
| PostgreSQL Bulk Loader | PGBulkLoader |
| test data | DataGrid |

## Hops

| From | To |
|------|----|
| test data | PostgreSQL Bulk Loader |
