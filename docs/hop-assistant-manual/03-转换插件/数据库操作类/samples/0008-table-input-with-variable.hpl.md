# Pipeline: 0008-table-input-with-variable

## Basic Information

- **Pipeline Name:** 0008-table-input-with-variable
- **Source File:** `03-转换插件/数据库操作类/samples/0008-table-input-with-variable.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Table input | TableInput |
| check count | FilterRows |
| count rows | GroupBy |
| key variable | RowGenerator |
| success | Dummy |

## Hops

| From | To |
|------|----|
| Table input | count rows |
| count rows | check count |
| check count | success |
| check count | Abort |
| key variable | Table input |
