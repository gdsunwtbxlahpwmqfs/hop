# Pipeline: 0036-actionpipeline-getresults-fromresults

## Basic Information

- **Pipeline Name:** 0036-actionpipeline-getresults-fromresults
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-actionpipeline-getresults-fromresults.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort 1 | Abort |
| Count = 1 | FilterRows |
| Count Rows | GroupBy |
| Detect empty stream | DetectEmptyStream |
| Success | Dummy |
| Get rows from result | RowsFromResult |
| Abort 2 | Abort |

## Hops

| From | To |
|------|----|
| Get rows from result | Count Rows |
| Count Rows | Count = 1 |
| Count = 1 | Success |
| Get rows from result | Detect empty stream |
| Detect empty stream | Abort 1 |
| Count = 1 | Abort 2 |
