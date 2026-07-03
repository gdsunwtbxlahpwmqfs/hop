# Pipeline: null-if-basic

## Basic Information

- **Pipeline Name:** null-if-basic
- **Source File:** `03-转换插件/字符串与文本处理类/samples/null-if-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Null if two, four | NullIf |
| Ouput | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Null if two, four |
| Null if two, four | Ouput |

## Notes

Sets the value for the field 'desc' to null for values 'two' and 'four'

---
