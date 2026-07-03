# Pipeline: loops-get-rows-from-result

## Basic Information

- **Pipeline Name:** loops-get-rows-from-result
- **Source File:** `03-转换插件/映射与子管道类/samples/child-loops-get-rows-from-result.hpl`

## Transforms

| Name | Type |
|------|------|
| count rows received | MemoryGroupBy |
| get rows from result | RowsFromResult |
| log nb_rows | WriteToLog |

## Hops

| From | To |
|------|----|
| get rows from result | count rows received |
| count rows received | log nb_rows |
