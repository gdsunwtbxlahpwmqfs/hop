# Pipeline: 0009-database-join-normal

## Basic Information

- **Pipeline Name:** 0009-database-join-normal
- **Source File:** `03-转换插件/数据库操作类/samples/0009-database-join-normal.hpl`

## Transforms

| Name | Type |
|------|------|
| Database join | DBJoin |
| Generate rows | RowGenerator |
| count rows | GroupBy |
| failed on count | Abort |
| success | Dummy |
| validate count | FilterRows |

## Hops

| From | To |
|------|----|
| Generate rows | Database join |
| Database join | count rows |
| count rows | validate count |
| validate count | success |
| validate count | failed on count |
