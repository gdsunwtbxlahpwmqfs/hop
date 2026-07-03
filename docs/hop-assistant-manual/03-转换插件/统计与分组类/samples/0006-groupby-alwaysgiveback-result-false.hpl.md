# Pipeline: 0006-groupby-alwaysgiveback-result-false

## Basic Information

- **Pipeline Name:** 0006-groupby-alwaysgiveback-result-false
- **Source File:** `03-转换插件/统计与分组类/samples/0006-groupby-alwaysgiveback-result-false.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Count = 0 | FilterRows |
| Data grid | DataGrid |
| Detect empty stream | DetectEmptyStream |
| Filter rows > 3 | FilterRows |
| Group by | GroupBy |
| Success | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Filter rows > 3 |
| Filter rows > 3 | Group by |
| Group by | Count = 0 |
| Group by | Detect empty stream |
| Detect empty stream | Success |
| Count = 0 | Abort |
