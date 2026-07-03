# Pipeline: table-compare

## Basic Information

- **Pipeline Name:** table-compare
- **Source File:** `03-转换插件/数据库操作类/samples/table-compare.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| ERRORS | Dummy |
| Table compare | TableCompare |
| Table results | Dummy |
| success log | WriteToLog |
| fail log | WriteToLog |

## Hops

| From | To |
|------|----|
| Data grid | Table compare |
| Table compare | ERRORS |
| Table compare | Table results |
| Table results | success log |
| ERRORS | fail log |
