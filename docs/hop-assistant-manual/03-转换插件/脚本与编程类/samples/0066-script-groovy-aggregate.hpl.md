# Pipeline: 0066-script-groovy-aggregate

## Basic Information

- **Pipeline Name:** 0066-script-groovy-aggregate
- **Source File:** `03-转换插件/脚本与编程类/samples/0066-script-groovy-aggregate.hpl`

## Transforms

| Name | Type |
|------|------|
| Aggregate with Groovy | SuperScript |
| Verify | Dummy |
| groups | DataGrid |
| nextGroup | AnalyticQuery |
| value | Sequence |
| x10 rows | CloneRow |

## Hops

| From | To |
|------|----|
| Aggregate with Groovy | Verify |
| groups | x10 rows |
| nextGroup | Aggregate with Groovy |
| value | nextGroup |
| x10 rows | value |
