# Pipeline: 0066-script-python-aggregate

## Basic Information

- **Pipeline Name:** 0066-script-python-aggregate
- **Source File:** `03-转换插件/脚本与编程类/samples/0066-script-python-aggregate.hpl`

## Transforms

| Name | Type |
|------|------|
| Aggregate with Python | SuperScript |
| Verify | Dummy |
| groups | DataGrid |
| nextGroup | AnalyticQuery |
| value | Sequence |
| x10 rows | CloneRow |

## Hops

| From | To |
|------|----|
| Aggregate with Python | Verify |
| groups | x10 rows |
| nextGroup | Aggregate with Python |
| value | nextGroup |
| x10 rows | value |
