# Pipeline: 0019-split-field-to-rows-basic

## Basic Information

- **Pipeline Name:** 0019-split-field-to-rows-basic
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0019-split-field-to-rows-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Split movie to movies | SplitFieldToRows3 |
| Sample input | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Sample input | Split movie to movies |
| Split movie to movies | Verify |
