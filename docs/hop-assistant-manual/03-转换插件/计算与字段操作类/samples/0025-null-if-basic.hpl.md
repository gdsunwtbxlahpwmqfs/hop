# Pipeline: 0025-null-if-basic

## Basic Information

- **Pipeline Name:** 0025-null-if-basic
- **Source File:** `03-转换插件/计算与字段操作类/samples/0025-null-if-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Null if | NullIf |
| Preview | Dummy |
| Sample data | DataGrid |
| Null if 2 | NullIf |

## Hops

| From | To |
|------|----|
| Null if | Preview |
| Sample data | Null if 2 |
| Null if 2 | Null if |
