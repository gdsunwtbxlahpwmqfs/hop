# Pipeline: 0007-database-lookup

## Basic Information

- **Pipeline Name:** 0007-database-lookup
- **Source File:** `03-转换插件/数据库操作类/samples/0007-database-lookup.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Database lookup | DBLookup |
| check count | FilterRows |
| lookup row | RowGenerator |
| success | Dummy |

## Hops

| From | To |
|------|----|
| lookup row | Database lookup |
| check count | Abort |
| check count | success |
| Database lookup | check count |
