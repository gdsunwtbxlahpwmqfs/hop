# Pipeline: 0008-table-input-with-sql-file

## Basic Information

- **Pipeline Name:** 0008-table-input-with-sql-file
- **Source File:** `03-转换插件/数据库操作类/samples/0008-table-input-with-sql-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Table input | TableInput |
| check count | FilterRows |
| count rows | GroupBy |
| success | Dummy |

## Hops

| From | To |
|------|----|
| Table input | count rows |
| count rows | check count |
| check count | success |
| check count | Abort |
