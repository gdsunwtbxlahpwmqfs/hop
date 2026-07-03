# Pipeline: 0002-read_parquet_from_azure_azure_scheme

## Basic Information

- **Pipeline Name:** 0002-read_parquet_from_azure_azure_scheme
- **Source File:** `03-转换插件/云服务类/samples/0002-read_parquet_from_azure_azure_scheme.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Generate rows Azurite (azure scheme) | RowGenerator |
| Parquet File Input | ParquetFileInput |

## Hops

| From | To |
|------|----|
| Parquet File Input | Dummy (do nothing) |
| Generate rows Azurite (azure scheme) | Parquet File Input |
