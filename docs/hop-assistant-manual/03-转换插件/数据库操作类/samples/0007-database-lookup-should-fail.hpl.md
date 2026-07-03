# Pipeline: 0007-database-lookup-should-fail

## Basic Information

- **Pipeline Name:** 0007-database-lookup-should-fail
- **Source File:** `03-转换插件/数据库操作类/samples/0007-database-lookup-should-fail.hpl`

## Transforms

| Name | Type |
|------|------|
| Database lookup | DBLookup |
| lookup row | RowGenerator |

## Hops

| From | To |
|------|----|
| lookup row | Database lookup |

## Notes

Multiple records fail check

---
