# Pipeline: 0025-table-compare-validation

## Basic Information

- **Pipeline Name:** 0025-table-compare-validation
- **Source File:** `03-转换插件/数据库操作类/samples/0025-table-compare-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| ERRORS | Dummy |
| Table compare | TableCompare |
| Table results | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Table compare |
| Table compare | ERRORS |
| Table compare | Table results |
