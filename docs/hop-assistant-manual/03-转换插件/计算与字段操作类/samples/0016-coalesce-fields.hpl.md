# Pipeline: 0016-coalesce-fields

## Basic Information

- **Pipeline Name:** 0016-coalesce-fields
- **Source File:** `03-转换插件/计算与字段操作类/samples/0016-coalesce-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| Coalesce Fields | Coalesce |
| Input | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Input | Coalesce Fields |
| Coalesce Fields | Verify |
