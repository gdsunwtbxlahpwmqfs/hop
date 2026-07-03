# Pipeline: insertupdate-basic

## Basic Information

- **Pipeline Name:** insertupdate-basic
- **Source File:** `03-转换插件/数据库操作类/samples/insertupdate-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Insert / update | InsertUpdate |
| Test Data | DataGrid |

## Hops

| From | To |
|------|----|
| Test Data | Insert / update |

## Notes

Writes 7 lines of data to a table, 2 of which are updates to earlier records.

Id is used as the key (unique identifier). Values for col_bool and col_string are updated, values for id and col_date aren't

---
