# Pipeline: 0024-get-table-names-validation

## Basic Information

- **Pipeline Name:** 0024-get-table-names-validation
- **Source File:** `03-转换插件/数据库操作类/samples/0024-get-table-names-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| 0024 tables | FilterRows |
| Get table names | GetTableNames |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Get table names | 0024 tables |
| 0024 tables | Verify |
