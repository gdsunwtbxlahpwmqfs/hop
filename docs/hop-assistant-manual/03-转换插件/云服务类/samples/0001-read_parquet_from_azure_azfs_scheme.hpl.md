# Pipeline: 0001-read_parquet_from_azure_azfs_scheme

## Basic Information

- **Pipeline Name:** 0001-read_parquet_from_azure_azfs_scheme
- **Source File:** `03-转换插件/云服务类/samples/0001-read_parquet_from_azure_azfs_scheme.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Generate rows Azurite (azfs scheme) | RowGenerator |
| Parquet File Input | ParquetFileInput |

## Hops

| From | To |
|------|----|
| Parquet File Input | Dummy (do nothing) |
| Generate rows Azurite (azfs scheme) | Parquet File Input |
