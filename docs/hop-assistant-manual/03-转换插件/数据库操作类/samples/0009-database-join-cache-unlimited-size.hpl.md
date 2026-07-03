# Pipeline: 0009-database-join-cache-unlimited-size

## Basic Information

- **Pipeline Name:** 0009-database-join-cache-unlimited-size
- **Source File:** `03-转换插件/数据库操作类/samples/0009-database-join-cache-unlimited-size.hpl`

## Transforms

| Name | Type |
|------|------|
| Database join - cache of unlimited size enabled | DBJoin |
| Generate Rows | DataGrid |
| count rows | GroupBy |
| failed on count | Abort |
| success | Dummy |
| validate count | FilterRows |

## Hops

| From | To |
|------|----|
| Database join - cache of unlimited size enabled | count rows |
| count rows | validate count |
| validate count | failed on count |
| validate count | success |
| Generate Rows | Database join - cache of unlimited size enabled |
