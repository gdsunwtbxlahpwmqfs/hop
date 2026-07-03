# Pipeline: 0032-fields-change-sequence

## Basic Information

- **Pipeline Name:** 0032-fields-change-sequence
- **Source File:** `03-转换插件/计算与字段操作类/samples/0032-fields-change-sequence.hpl`

## Transforms

| Name | Type |
|------|------|
| Add value fields changing sequence | FieldsChangeSequence |
| Data grid | DataGrid |
| Result | Dummy |
| sort | SortRows |

## Hops

| From | To |
|------|----|
| sort | Add value fields changing sequence |
| Data grid | sort |
| Add value fields changing sequence | Result |
