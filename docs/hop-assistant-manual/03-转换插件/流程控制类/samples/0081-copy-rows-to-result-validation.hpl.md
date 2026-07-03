# Pipeline: 0081-copy-rows-to-result-validation

## Basic Information

- **Pipeline Name:** 0081-copy-rows-to-result-validation
- **Source File:** `03-转换插件/流程控制类/samples/0081-copy-rows-to-result-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Get rows from result | RowsFromResult |
| check if 4 rows | FilterRows |
| count rows | MemoryGroupBy |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| count rows | check if 4 rows |
| check if 4 rows | Abort |
| Get rows from result | Write to log |
| Write to log | count rows |
