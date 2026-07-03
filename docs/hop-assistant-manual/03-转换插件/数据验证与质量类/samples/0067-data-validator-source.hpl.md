# Pipeline: 0067-data-validator-source

## Basic Information

- **Pipeline Name:** 0067-data-validator-source
- **Source File:** `03-转换插件/数据验证与质量类/samples/0067-data-validator-source.hpl`

## Transforms

| Name | Type |
|------|------|
| Errors | Dummy |
| Output | Dummy |
| Validate data | Validator |
| allowed values | DataGrid |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| Validate data | Errors |
| Validate data | Output |
| sample data | Validate data |
| allowed values | Validate data |
