# Pipeline: 0003-field-splitter-enclosure

## Basic Information

- **Pipeline Name:** 0003-field-splitter-enclosure
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0003-field-splitter-enclosure.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| OK | Dummy |
| Split fields | FieldSplitter |
| string | DataGrid |
| validation | ConcatFields |
| verify | FilterRows |

## Hops

| From | To |
|------|----|
| Split fields | validation |
| string | Split fields |
| validation | verify |
| verify | OK |
| verify | Abort |

## Notes

NOTE: If variable

HOP_SPLIT_FIELDS_REMOVE_ENCLOSURE

is set, the enclosure is removed from the split strings

---
