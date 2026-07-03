# Pipeline: 0007-analytic-query-no-group

## Basic Information

- **Pipeline Name:** 0007-analytic-query-no-group
- **Source File:** `03-转换插件/统计与分组类/samples/0007-analytic-query-no-group.hpl`

## Transforms

| Name | Type |
|------|------|
| Lead/Lag | AnalyticQuery |
| Test input data | DataGrid |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| Test input data | Lead/Lag |
| Lead/Lag | Validate |
