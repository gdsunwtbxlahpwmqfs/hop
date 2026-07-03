# Pipeline: 0002-monetdb-bulk-loader

## Basic Information

- **Pipeline Name:** 0002-monetdb-bulk-loader
- **Source File:** `03-转换插件/数据库操作类/samples/0002-monetdb-bulk-loader.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| MonetDB bulk loader | MonetDBBulkLoader |

## Hops

| From | To |
|------|----|
| Generate rows | MonetDB bulk loader |
