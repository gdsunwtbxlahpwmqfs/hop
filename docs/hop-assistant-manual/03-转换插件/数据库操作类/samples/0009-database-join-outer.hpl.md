# Pipeline: 0009-database-join-outer

## Basic Information

- **Pipeline Name:** 0009-database-join-outer
- **Source File:** `03-转换插件/数据库操作类/samples/0009-database-join-outer.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Database join | DBJoin |
| count rows | GroupBy |
| validate count | FilterRows |
| success | Dummy |
| failed on count | Abort |
| failed on null value | Abort |
| validate count 2 | FilterRows |

## Hops

| From | To |
|------|----|
| Generate rows | Database join |
| count rows | validate count |
| validate count | success |
| validate count | failed on count |
| validate count 2 | failed on null value |
| Database join | validate count 2 |
| validate count 2 | count rows |
