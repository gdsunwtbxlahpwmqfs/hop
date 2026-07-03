# Pipeline: 0066-script-javascript-aggregate

## Basic Information

- **Pipeline Name:** 0066-script-javascript-aggregate
- **Source File:** `03-转换插件/脚本与编程类/samples/0066-script-javascript-aggregate.hpl`

## Transforms

| Name | Type |
|------|------|
| Aggregate with JavaScript | SuperScript |
| Verify | Dummy |
| groups | DataGrid |
| nextGroup | AnalyticQuery |
| value | Sequence |
| x10 rows | CloneRow |

## Hops

| From | To |
|------|----|
| nextGroup | Aggregate with JavaScript |
| groups | x10 rows |
| x10 rows | value |
| value | nextGroup |
| Aggregate with JavaScript | Verify |
