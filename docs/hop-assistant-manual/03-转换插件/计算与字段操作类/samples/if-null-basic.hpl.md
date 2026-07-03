# Pipeline: if-null-basic

## Basic Information

- **Pipeline Name:** if-null-basic
- **Source File:** `03-转换插件/计算与字段操作类/samples/if-null-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| If 'desc' Null | IfNull |
| Output 'desc' | Dummy |
| If Dates Null | IfNull |
| Output Dates | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | If 'desc' Null |
| If 'desc' Null | Output 'desc' |
| Test Data | If Dates Null |
| If Dates Null | Output Dates |

## Notes

Add a value when a field or data type is null

*) If 'desc' null: check if a field is null, replace with a hard coded value or variable if it is

*) If Dates null: check all fields of a given data type, replace null values with a hard coded value or variable value

---
