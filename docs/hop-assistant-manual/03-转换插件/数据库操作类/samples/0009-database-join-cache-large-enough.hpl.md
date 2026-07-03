# Pipeline: 0009-database-join-cache-large-enough

## Basic Information

- **Pipeline Name:** 0009-database-join-cache-large-enough
- **Source File:** `03-转换插件/数据库操作类/samples/0009-database-join-cache-large-enough.hpl`

## Transforms

| Name | Type |
|------|------|
| Database join - cache of size very big | DBJoin |
| Generate Rows | DataGrid |
| count rows | GroupBy |
| failed on count | Abort |
| success | Dummy |
| validate count | FilterRows |

## Hops

| From | To |
|------|----|
| Database join - cache of size very big | count rows |
| Generate Rows | Database join - cache of size very big |
| count rows | validate count |
| validate count | failed on count |
| validate count | success |
