# Pipeline: 0008-analytic-query-child

## Basic Information

- **Pipeline Name:** 0008-analytic-query-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0008-analytic-query-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Lead/Lag | AnalyticQuery |
| Test input data | DataGrid |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Lead/Lag | Output |
| Test input data | Lead/Lag |
