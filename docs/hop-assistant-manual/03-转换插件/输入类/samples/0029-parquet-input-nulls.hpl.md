# Pipeline: 0029-parquet-input-nulls

## Basic Information

- **Pipeline Name:** 0029-parquet-input-nulls
- **Source File:** `03-转换插件/输入类/samples/0029-parquet-input-nulls.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/it/parquet/nulls.parquet | GetFileNames |
| Parquet File Input | ParquetFileInput |
| only filename | SelectValues |
| Validate | Dummy |
| remove filename | SelectValues |

## Hops

| From | To |
|------|----|
| ${java.io.tmpdir}/it/parquet/nulls.parquet | only filename |
| only filename | Parquet File Input |
| Parquet File Input | remove filename |
| remove filename | Validate |
