# Pipeline: 0003-field-splitter-using-ids

## Basic Information

- **Pipeline Name:** 0003-field-splitter-using-ids
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0003-field-splitter-using-ids.hpl`

## Transforms

| Name | Type |
|------|------|
| Split fields | FieldSplitter |
| string | DataGrid |
| validation | ConcatFields |
| verify | FilterRows |
| OK | Dummy |
| Abort | Abort |
| sum | GroupBy |

## Hops

| From | To |
|------|----|
| string | Split fields |
| validation | verify |
| verify | OK |
| verify | Abort |
| Split fields | sum |
| sum | validation |
