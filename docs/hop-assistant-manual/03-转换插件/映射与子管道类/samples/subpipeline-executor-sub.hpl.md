# Pipeline: subpipeline-executor-sub

## Basic Information

- **Pipeline Name:** subpipeline-executor-sub
- **Source File:** `03-转换插件/映射与子管道类/samples/subpipeline-executor-sub.hpl`

## Transforms

| Name | Type |
|------|------|
| Get rows from result | RowsFromResult |
| Group by | GroupBy |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Get rows from result | Group by |
| Group by | Write to log |
