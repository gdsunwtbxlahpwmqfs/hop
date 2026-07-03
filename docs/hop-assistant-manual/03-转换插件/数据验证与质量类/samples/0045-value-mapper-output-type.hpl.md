# Pipeline: 0045-value-mapper-output-type

## Basic Information

- **Pipeline Name:** 0045-value-mapper-output-type
- **Source File:** `03-转换插件/数据验证与质量类/samples/0045-value-mapper-output-type.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Data validator | Validator |
| Value mapper | ValueMapper |

## Hops

| From | To |
|------|----|
| Data grid | Value mapper |
| Value mapper | Data validator |
