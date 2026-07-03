# Pipeline: value-mapper-basic

## Basic Information

- **Pipeline Name:** value-mapper-basic
- **Source File:** `03-转换插件/统计与分组类/samples/value-mapper-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Currency Codes | DataGrid |
| Map Currency Name | ValueMapper |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Currency Codes | Map Currency Name |
| Map Currency Name | Output |

## Notes

Map a list of values to other values, e.g. currency codes to currency names and write to a new field or overwrite the original field.

---
