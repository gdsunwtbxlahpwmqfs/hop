# Pipeline: 0005-read_parquet_from_azure_multi_account

## Basic Information

- **Pipeline Name:** 0005-read_parquet_from_azure_multi_account
- **Source File:** `03-转换插件/云服务类/samples/0005-read_parquet_from_azure_multi_account.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Generate rows Azurite | RowGenerator |
| Parquet File Input | ParquetFileInput |

## Hops

| From | To |
|------|----|
| Parquet File Input | Dummy (do nothing) |
| Generate rows Azurite | Parquet File Input |
