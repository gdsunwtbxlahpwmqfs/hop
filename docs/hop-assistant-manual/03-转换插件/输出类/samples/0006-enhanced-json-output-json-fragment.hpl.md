# Pipeline: 0006-enhanced-json-output-json-fragment

## Basic Information

- **Pipeline Name:** 0006-enhanced-json-output-json-fragment
- **Source File:** `03-转换插件/输出类/samples/0006-enhanced-json-output-json-fragment.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Enhanced JSON Output | EnhancedJsonOutput |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Enhanced JSON Output |
| Enhanced JSON Output | Dummy (do nothing) |
