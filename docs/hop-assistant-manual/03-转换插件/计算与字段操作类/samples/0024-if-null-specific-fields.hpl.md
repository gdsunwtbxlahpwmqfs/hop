# Pipeline: 0024-if-null-specific-fields

## Basic Information

- **Pipeline Name:** 0024-if-null-specific-fields
- **Source File:** `03-转换插件/计算与字段操作类/samples/0024-if-null-specific-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| If Null - fields a,c,d,f | IfNull |
| Sample data | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Sample data | If Null - fields a,c,d,f |
| If Null - fields a,c,d,f | Verify |
