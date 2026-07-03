# Pipeline: 0072-read-parquet-file

## Basic Information

- **Pipeline Name:** 0072-read-parquet-file
- **Source File:** `03-转换插件/输入类/samples/0072-read-parquet-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Calculator | Calculator |
| Dummy (do nothing) | Dummy |
| Generate rows | RowGenerator |
| PROJECT_HOME | GetVariable |
| Parquet File Input | ParquetFileInput |

## Hops

| From | To |
|------|----|
| Parquet File Input | Dummy (do nothing) |
| Generate rows | PROJECT_HOME |
| PROJECT_HOME | Calculator |
| Calculator | Parquet File Input |
