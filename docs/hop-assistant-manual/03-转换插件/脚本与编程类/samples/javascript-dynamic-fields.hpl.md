# Pipeline: javascript-dynamic-fields

## Basic Information

- **Pipeline Name:** javascript-dynamic-fields
- **Source File:** `03-转换插件/脚本与编程类/samples/javascript-dynamic-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| append streams | Append |
| dummy data | DataGrid |
| read field names dynamically | ScriptValueMod |
| read field values dynamically | ScriptValueMod |
| rename field_names | SelectValues |
| rename field_values | SelectValues |
| unique values (single row) | SortRows |
| write csv | TextFileOutput |

## Hops

| From | To |
|------|----|
| dummy data | read field values dynamically |
| dummy data | read field names dynamically |
| read field names dynamically | unique values (single row) |
| unique values (single row) | rename field_names |
| read field values dynamically | rename field_values |
| rename field_names | append streams |
| rename field_values | append streams |
| append streams | write csv |

## Notes

read the field names and values in Javascript and combine field names and values as header + data rows that are written to a CSV file.

---
