# Pipeline: 0007-database-lookup-empty-result

## Basic Information

- **Pipeline Name:** 0007-database-lookup-empty-result
- **Source File:** `03-转换插件/数据库操作类/samples/0007-database-lookup-empty-result.hpl`

## Transforms

| Name | Type |
|------|------|
| lookup row | RowGenerator |
| Database lookup | DBLookup |
| Group by | GroupBy |
| Abort | Abort |
| check count | FilterRows |
| success | Dummy |

## Hops

| From | To |
|------|----|
| lookup row | Database lookup |
| Database lookup | Group by |
| check count | Abort |
| check count | success |
| Group by | check count |

## Notes

Do not pass row when no result chek

---
