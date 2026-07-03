# Pipeline: 0028-set-field-value

## Basic Information

- **Pipeline Name:** 0028-set-field-value
- **Source File:** `03-转换插件/计算与字段操作类/samples/0028-set-field-value.hpl`

## Transforms

| Name | Type |
|------|------|
| value2 -> value | SetValueField |
| Sample data | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Sample data | value2 -> value |
| value2 -> value | Verify |
