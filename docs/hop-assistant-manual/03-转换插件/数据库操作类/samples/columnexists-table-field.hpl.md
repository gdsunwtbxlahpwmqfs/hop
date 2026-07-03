# Pipeline: columnexists-table-field

## Basic Information

- **Pipeline Name:** columnexists-table-field
- **Source File:** `03-转换插件/数据库操作类/samples/columnexists-table-field.hpl`

## Transforms

| Name | Type |
|------|------|
| tables, columns | DataGrid |
| Column exists | ColumnExists |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| tables, columns | Column exists |
| Column exists | Output |

## Notes

Checks a table for a list of tables and columns, returns true if a columns exists, false if it doesn't

---
