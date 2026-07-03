# Pipeline: 0004-test-header-check

## Basic Information

- **Pipeline Name:** 0004-test-header-check
- **Source File:** `03-转换插件/输入类/samples/0004-test-header-check.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Filter rows | FilterRows |
| add row if no rows | DetectEmptyStream |
| get max value and number of rows | MemoryGroupBy |
| read header xlsx | ExcelInput |

## Hops

| From | To |
|------|----|
| read header xlsx | add row if no rows |
| add row if no rows | get max value and number of rows |
| get max value and number of rows | Filter rows |
| Filter rows | Abort |
| read header xlsx | get max value and number of rows |
