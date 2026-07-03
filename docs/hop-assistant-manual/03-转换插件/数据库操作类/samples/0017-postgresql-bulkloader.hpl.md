# Pipeline: 0017-postgresql-bulkloader

## Basic Information

- **Pipeline Name:** 0017-postgresql-bulkloader
- **Source File:** `03-转换插件/数据库操作类/samples/0017-postgresql-bulkloader.hpl`

## Transforms

| Name | Type |
|------|------|
| PostgreSQL Bulk Loader | PGBulkLoader |
| test data | DataGrid |

## Hops

| From | To |
|------|----|
| test data | PostgreSQL Bulk Loader |
