# Pipeline: 0024-if-null-by-type

## Basic Information

- **Pipeline Name:** 0024-if-null-by-type
- **Source File:** `03-转换插件/计算与字段操作类/samples/0024-if-null-by-type.hpl`

## Transforms

| Name | Type |
|------|------|
| If Null - types | IfNull |
| Sample data | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| If Null - types | Verify |
| Sample data | If Null - types |
