# Pipeline: 0029-parquet-output-nulls

## Basic Information

- **Pipeline Name:** 0029-parquet-output-nulls
- **Source File:** `03-转换插件/输出类/samples/0029-parquet-output-nulls.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/nulls.parquet | ParquetFileOutput |
| data with nulls | DataGrid |

## Hops

| From | To |
|------|----|
| data with nulls | ${java.io.tmpdir}/nulls.parquet |
