# Pipeline: 0036-actionpipeline-verify-results-notavailable

## Basic Information

- **Pipeline Name:** 0036-actionpipeline-verify-results-notavailable
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-actionpipeline-verify-results-notavailable.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Count = 1 | FilterRows |
| Count Rows | GroupBy |
| Detect empty stream | DetectEmptyStream |
| Get rows from result | RowsFromResult |
| Success! | Dummy |
| Log Abort | WriteToLog |

## Hops

| From | To |
|------|----|
| Get rows from result | Count Rows |
| Count Rows | Count = 1 |
| Get rows from result | Detect empty stream |
| Detect empty stream | Success! |
| Count = 1 | Log Abort |
| Log Abort | Abort |
