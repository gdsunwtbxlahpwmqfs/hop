# Pipeline: 0001-json-valuetypes

## Basic Information

- **Pipeline Name:** 0001-json-valuetypes
- **Source File:** `03-转换插件/输入类/samples/0001-json-valuetypes.hpl`

## Transforms

| Name | Type |
|------|------|
| Append streams | Append |
| Convert to Json Node | SelectValues |
| Dummy | Dummy |
| Json Node | DataGrid |
| Json String | DataGrid |

## Hops

| From | To |
|------|----|
| Json String | Convert to Json Node |
| Json Node | Append streams |
| Convert to Json Node | Append streams |
| Append streams | Dummy |
