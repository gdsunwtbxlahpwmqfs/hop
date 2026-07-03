# Pipeline: 0029-sql-file-output-validate-file

## Basic Information

- **Pipeline Name:** 0029-sql-file-output-validate-file
- **Source File:** `03-转换插件/数据库操作类/samples/0029-sql-file-output-validate-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Data grid | DataGrid |
| Filter rows | FilterRows |
| Merge join | MergeJoin |
| Text file input | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Data grid | Merge join |
| Text file input | Merge join |
| Merge join | Filter rows |
| Filter rows | Abort |
