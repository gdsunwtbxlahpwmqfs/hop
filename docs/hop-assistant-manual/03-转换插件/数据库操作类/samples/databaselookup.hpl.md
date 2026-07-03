# Pipeline: databaselookup

## Basic Information

- **Pipeline Name:** databaselookup
- **Source File:** `03-转换插件/数据库操作类/samples/databaselookup.hpl`

## Transforms

| Name | Type |
|------|------|
| Database lookup | DBLookup |
| Output | Dummy |
| Test Data | DataGrid |

## Hops

| From | To |
|------|----|
| Test Data | Database lookup |
| Database lookup | Output |

## Notes

Looks up descriptions for an id in a database table

---
