# Pipeline: databasejoin-basic

## Basic Information

- **Pipeline Name:** databasejoin-basic
- **Source File:** `03-转换插件/数据库操作类/samples/databasejoin-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Database join | DBJoin |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Database join |
| Database join | Output |

## Notes

Fetches the description for a given id through a database join

---
