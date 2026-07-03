# Pipeline: 0001-write_parquet_to_azure_azfs_scheme

## Basic Information

- **Pipeline Name:** 0001-write_parquet_to_azure_azfs_scheme
- **Source File:** `03-转换插件/云服务类/samples/0001-write_parquet_to_azure_azfs_scheme.hpl`

## Transforms

| Name | Type |
|------|------|
| Read employees data from dataset | TextFileInput2 |
| Write to Azurite a Parquet file (azfs) | ParquetFileOutput |

## Hops

| From | To |
|------|----|
| Read employees data from dataset | Write to Azurite a Parquet file (azfs) |
