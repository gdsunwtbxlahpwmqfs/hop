# Pipeline: 0003-field-splitter-escape-string

## Basic Information

- **Pipeline Name:** 0003-field-splitter-escape-string
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0003-field-splitter-escape-string.hpl`

## Transforms

| Name | Type |
|------|------|
| Split fields | FieldSplitter |
| string | DataGrid |
| validation | ConcatFields |
| verify | FilterRows |
| OK | Dummy |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Split fields | validation |
| string | Split fields |
| validation | verify |
| verify | OK |
| verify | Abort |
