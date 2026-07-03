# Pipeline: 0002-pipeline-executor-grouponfield-checkrowsnum-called

## Basic Information

- **Pipeline Name:** 0002-pipeline-executor-grouponfield-checkrowsnum-called
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-pipeline-executor-grouponfield-checkrowsnum-called.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Get rows from result | RowsFromResult |
| Group by | GroupBy |
| Success | Dummy |
| Write to log | WriteToLog |
| tot = 3 | FilterRows |

## Hops

| From | To |
|------|----|
| tot = 3 | Abort |
| Get rows from result | Group by |
| tot = 3 | Success |
| Group by | Write to log |
| Write to log | tot = 3 |
