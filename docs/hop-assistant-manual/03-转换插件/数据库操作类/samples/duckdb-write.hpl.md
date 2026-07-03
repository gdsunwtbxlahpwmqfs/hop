# Pipeline: duckdb-write

## Basic Information

- **Pipeline Name:** duckdb-write
- **Source File:** `03-转换插件/数据库操作类/samples/duckdb-write.hpl`

## Transforms

| Name | Type |
|------|------|
| fake book data | Fake |
| generate 100k rows | RowGenerator |
| write book data | TableOutput |

## Hops

| From | To |
|------|----|
| generate 100k rows | fake book data |
| fake book data | write book data |
