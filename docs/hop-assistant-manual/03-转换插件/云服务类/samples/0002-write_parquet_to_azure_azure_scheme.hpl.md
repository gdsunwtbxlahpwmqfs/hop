# Pipeline: 0002-write_parquet_to_azure_azure_scheme

## Basic Information

- **Pipeline Name:** 0002-write_parquet_to_azure_azure_scheme
- **Source File:** `03-转换插件/云服务类/samples/0002-write_parquet_to_azure_azure_scheme.hpl`

## Transforms

| Name | Type |
|------|------|
| Read employees data from dataset | TextFileInput2 |
| Write to Azurite a Parquet file (azure) | ParquetFileOutput |

## Hops

| From | To |
|------|----|
| Read employees data from dataset | Write to Azurite a Parquet file (azure) |
