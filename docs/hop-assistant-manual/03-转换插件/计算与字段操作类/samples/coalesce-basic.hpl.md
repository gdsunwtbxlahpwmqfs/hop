# Pipeline: coalesce-basic

## Basic Information

- **Pipeline Name:** coalesce-basic
- **Source File:** `03-转换插件/计算与字段操作类/samples/coalesce-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Coalesce Fields | Coalesce |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Coalesce Fields |
| Coalesce Fields | Output |

## Notes

Selects the first non-null value from two description fields, stores in a new 'desc' field.

---
