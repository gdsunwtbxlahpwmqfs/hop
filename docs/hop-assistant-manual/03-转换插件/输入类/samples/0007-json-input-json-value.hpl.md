# Pipeline: 0007-json-input-json-value

## Basic Information

- **Pipeline Name:** 0007-json-input-json-value
- **Source File:** `03-转换插件/输入类/samples/0007-json-input-json-value.hpl`

## Transforms

| Name | Type |
|------|------|
| Data validator | Validator |
| Dummy JSON | DataGrid |
| Get Attributes | JsonInput |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| Dummy JSON | Get Attributes |
| Get Attributes | Data validator |
| Get Attributes | Validate |
