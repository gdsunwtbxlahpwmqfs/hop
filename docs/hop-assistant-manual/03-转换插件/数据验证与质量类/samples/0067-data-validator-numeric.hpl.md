# Pipeline: 0067-data-validator-numeric

## Basic Information

- **Pipeline Name:** 0067-data-validator-numeric
- **Source File:** `03-转换插件/数据验证与质量类/samples/0067-data-validator-numeric.hpl`

## Transforms

| Name | Type |
|------|------|
| Data validator | Validator |
| Errors | Dummy |
| Output | Dummy |
| Sample data | DataGrid |

## Hops

| From | To |
|------|----|
| Sample data | Data validator |
| Data validator | Output |
| Data validator | Errors |
