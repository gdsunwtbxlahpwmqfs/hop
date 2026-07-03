# Pipeline: string-operations-basic

## Basic Information

- **Pipeline Name:** string-operations-basic
- **Source File:** `03-转换插件/字符串与文本处理类/samples/string-operations-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| String operations | StringOperations |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | String operations |
| String operations | Output |

## Notes

Perform various operations on string fields: trim, change case, pad, init cap

---
