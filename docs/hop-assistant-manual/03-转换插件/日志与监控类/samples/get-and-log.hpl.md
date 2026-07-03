# Pipeline: get-and-log

## Basic Information

- **Pipeline Name:** get-and-log
- **Source File:** `03-转换插件/日志与监控类/samples/get-and-log.hpl`

## Transforms

| Name | Type |
|------|------|
| Get rows from result | RowsFromResult |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Get rows from result | Write to log |
