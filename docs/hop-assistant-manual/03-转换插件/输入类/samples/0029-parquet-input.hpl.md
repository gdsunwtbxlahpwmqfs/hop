# Pipeline: 0029-parquet-input

## Basic Information

- **Pipeline Name:** 0029-parquet-input
- **Source File:** `03-转换插件/输入类/samples/0029-parquet-input.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/it/parquet/customers.parquet.snappy | GetFileNames |
| Parquet File Input | ParquetFileInput |
| Verify | Dummy |
| only filename | SelectValues |

## Hops

| From | To |
|------|----|
| ${java.io.tmpdir}/it/parquet/customers.parquet.snappy | only filename |
| only filename | Parquet File Input |
| Parquet File Input | Verify |
