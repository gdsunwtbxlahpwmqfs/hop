# Pipeline: 0067-data-validator-basic

## Basic Information

- **Pipeline Name:** 0067-data-validator-basic
- **Source File:** `03-转换插件/数据验证与质量类/samples/0067-data-validator-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Errors | Dummy |
| Output | Dummy |
| Validate data | Validator |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| sample data | Validate data |
| Validate data | Errors |
| Validate data | Output |
