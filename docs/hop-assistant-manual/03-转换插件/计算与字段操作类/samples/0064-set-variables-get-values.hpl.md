# Pipeline: 0064-set-variables-get-values

## Basic Information

- **Pipeline Name:** 0064-set-variables-get-values
- **Source File:** `03-转换插件/计算与字段操作类/samples/0064-set-variables-get-values.hpl`

## Transforms

| Name | Type |
|------|------|
| 1 row | RowGenerator |
| Filter rows | FilterRows |
| Get variables | GetVariable |
| Abort | Abort |
| OK | Dummy |

## Hops

| From | To |
|------|----|
| 1 row | Get variables |
| Get variables | Filter rows |
| Filter rows | OK |
| Filter rows | Abort |
