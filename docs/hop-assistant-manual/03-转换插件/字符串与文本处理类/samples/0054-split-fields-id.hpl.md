# Pipeline: 0054-split-fields-id

## Basic Information

- **Pipeline Name:** 0054-split-fields-id
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0054-split-fields-id.hpl`

## Transforms

| Name | Type |
|------|------|
| Split value | FieldSplitter |
| Verify | Dummy |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| Split value | Verify |
| sample data | Split value |
