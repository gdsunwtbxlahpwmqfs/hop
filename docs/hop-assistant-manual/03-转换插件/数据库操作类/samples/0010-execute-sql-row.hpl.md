# Pipeline: 0010-execute-sql-row

## Basic Information

- **Pipeline Name:** 0010-execute-sql-row
- **Source File:** `03-转换插件/数据库操作类/samples/0010-execute-sql-row.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| count rows | GroupBy |
| failed on count | Abort |
| success | Dummy |
| validate count | FilterRows |
| Dynamic SQL row | DynamicSqlRow |

## Hops

| From | To |
|------|----|
| count rows | validate count |
| validate count | success |
| validate count | failed on count |
| Generate rows | Dynamic SQL row |
| Dynamic SQL row | count rows |
