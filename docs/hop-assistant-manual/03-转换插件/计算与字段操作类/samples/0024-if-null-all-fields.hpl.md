# Pipeline: 0024-if-null-all-fields

## Basic Information

- **Pipeline Name:** 0024-if-null-all-fields
- **Source File:** `03-转换插件/计算与字段操作类/samples/0024-if-null-all-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| If Null - all fields | IfNull |
| Sample data | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Sample data | If Null - all fields |
| If Null - all fields | Verify |
