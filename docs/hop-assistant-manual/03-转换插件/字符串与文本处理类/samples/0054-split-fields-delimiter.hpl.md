# Pipeline: 0054-split-fields-delimiter

## Basic Information

- **Pipeline Name:** 0054-split-fields-delimiter
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0054-split-fields-delimiter.hpl`

## Transforms

| Name | Type |
|------|------|
| Split value | FieldSplitter |
| Verify | Dummy |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| sample data | Split value |
| Split value | Verify |
