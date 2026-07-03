# Pipeline: split-fields-basic

## Basic Information

- **Pipeline Name:** split-fields-basic
- **Source File:** `03-转换插件/字符串与文本处理类/samples/split-fields-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test data | DataGrid |
| Output | Dummy |
| Split record into fields | FieldSplitter |

## Hops

| From | To |
|------|----|
| Test data | Split record into fields |
| Split record into fields | Output |

## Notes

Split Delimited rows from input dataset

---
