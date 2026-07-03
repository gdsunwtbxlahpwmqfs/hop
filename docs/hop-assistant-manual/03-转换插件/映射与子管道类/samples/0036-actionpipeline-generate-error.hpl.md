# Pipeline: get_rows_and_boom

## Basic Information

- **Pipeline Name:** get_rows_and_boom
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-actionpipeline-generate-error.hpl`

## Transforms

| Name | Type |
|------|------|
| Write to log with boom | WriteToLog |
| Get rows from result | RowsFromResult |

## Hops

| From | To |
|------|----|
| Get rows from result | Write to log with boom |
