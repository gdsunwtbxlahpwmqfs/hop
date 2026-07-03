# Pipeline: 0011-called-pipeline

## Basic Information

- **Pipeline Name:** 0011-called-pipeline
- **Source File:** `03-转换插件/计算与字段操作类/samples/0011-called-pipeline.hpl`

## Transforms

| Name | Type |
|------|------|
| Copy rows to result | RowsToResult |
| Get rows from result | RowsFromResult |
| Write to log | WriteToLog |
| Detect empty stream | DetectEmptyStream |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Get rows from result | Write to log |
| Write to log | Copy rows to result |
| Get rows from result | Detect empty stream |
| Detect empty stream | Abort |
