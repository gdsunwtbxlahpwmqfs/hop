# Pipeline: 0002-json-negative-index

## Basic Information

- **Pipeline Name:** 0002-json-negative-index
- **Source File:** `03-转换插件/输入类/samples/0002-json-negative-index.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| JSON input | JsonInput |
| Filter rows | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Data grid | JSON input |
| JSON input | Filter rows |
| Filter rows | Abort |
