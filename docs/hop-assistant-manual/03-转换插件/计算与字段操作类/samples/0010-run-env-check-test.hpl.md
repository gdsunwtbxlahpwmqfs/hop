# Pipeline: 0010-run-env-check-test

## Basic Information

- **Pipeline Name:** 0010-run-env-check-test
- **Source File:** `03-转换插件/计算与字段操作类/samples/0010-run-env-check-test.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Filter rows | FilterRows |
| Generate rows | RowGenerator |
| Get variables | GetVariable |
| Test Succeeded | Dummy |

## Hops

| From | To |
|------|----|
| Generate rows | Get variables |
| Get variables | Filter rows |
| Filter rows | Test Succeeded |
| Filter rows | Abort |
