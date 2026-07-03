# Pipeline: 0013-test-overwrite-parameter

## Basic Information

- **Pipeline Name:** 0013-test-overwrite-parameter
- **Source File:** `03-转换插件/计算与字段操作类/samples/0013-test-overwrite-parameter.hpl`

## Transforms

| Name | Type |
|------|------|
| Get variables | GetVariable |
| Filter rows | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Get variables | Filter rows |
| Filter rows | Abort |
