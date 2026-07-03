# Pipeline: 0036-actionpipeline-verify-results-available

## Basic Information

- **Pipeline Name:** 0036-actionpipeline-verify-results-available
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-actionpipeline-verify-results-available.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Count = 1 | FilterRows |
| Count Rows | GroupBy |
| Detect empty stream | DetectEmptyStream |
| Dummy (do nothing) | Dummy |
| Get rows from result | RowsFromResult |

## Hops

| From | To |
|------|----|
| Get rows from result | Count Rows |
| Count Rows | Count = 1 |
| Count = 1 | Dummy (do nothing) |
| Get rows from result | Detect empty stream |
| Detect empty stream | Abort |
