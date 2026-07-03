# Pipeline: 0017-database-lookup

## Basic Information

- **Pipeline Name:** 0017-database-lookup
- **Source File:** `03-转换插件/数据库操作类/samples/0017-database-lookup.hpl`

## Transforms

| Name | Type |
|------|------|
| 100k rows | RowGenerator |
| Abort | Abort |
| count | GroupBy |
| count <> 100.000 | FilterRows |
| id | Sequence |
| myUuid | DBLookup |
| myUuid <> '-' | FilterRows |

## Hops

| From | To |
|------|----|
| 100k rows | id |
| id | myUuid |
| count | count <> 100.000 |
| count <> 100.000 | Abort |
| myUuid <> '-' | count |
| myUuid | myUuid <> '-' |
