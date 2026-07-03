# Pipeline: 0031-mysql-bulkloader

## Basic Information

- **Pipeline Name:** 0031-mysql-bulkloader
- **Source File:** `03-转换插件/数据库操作类/samples/0031-mysql-bulkloader.hpl`

## Transforms

| Name | Type |
|------|------|
| Fake data | Fake |
| Generate rows | RowGenerator |
| MySQL bulk loader | MySqlBulkLoader |

## Hops

| From | To |
|------|----|
| Fake data | MySQL bulk loader |
| Generate rows | Fake data |
