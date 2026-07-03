# Pipeline: javafilter-basic

## Basic Information

- **Pipeline Name:** javafilter-basic
- **Source File:** `03-转换插件/脚本与编程类/samples/javafilter-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Java filter | JavaFilter |
| Test Data | DataGrid |
| Output Matching | Dummy |
| Output Non-matching | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Java filter |
| Java filter | Output Matching |
| Java filter | Output Non-matching |

## Notes

Passes rows to a "Matching" or "Non-Matching" stream, based on a java expression.

---
