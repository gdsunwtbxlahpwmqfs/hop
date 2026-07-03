# Pipeline: 0023-number-range

## Basic Information

- **Pipeline Name:** 0023-number-range
- **Source File:** `03-转换插件/统计与分组类/samples/0023-number-range.hpl`

## Transforms

| Name | Type |
|------|------|
| Price range | NumberRange |
| Prices | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Prices | Price range |
| Price range | Verify |
