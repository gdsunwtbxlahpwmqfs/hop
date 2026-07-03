# Pipeline: duckdb-read

## Basic Information

- **Pipeline Name:** duckdb-read
- **Source File:** `03-转换插件/数据库操作类/samples/duckdb-read.hpl`

## Transforms

| Name | Type |
|------|------|
| 100k rows? | FilterRows |
| get nb_rows | GroupBy |
| read book_data | TableInput |
| Dummy (do nothing) | Dummy |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| read book_data | get nb_rows |
| get nb_rows | 100k rows? |
| 100k rows? | Dummy (do nothing) |
| 100k rows? | Abort |
