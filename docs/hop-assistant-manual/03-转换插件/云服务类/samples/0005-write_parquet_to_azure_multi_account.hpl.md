# Pipeline: 0005-write_parquet_to_azure_multi_account

## Basic Information

- **Pipeline Name:** 0005-write_parquet_to_azure_multi_account
- **Source File:** `03-转换插件/云服务类/samples/0005-write_parquet_to_azure_multi_account.hpl`

## Transforms

| Name | Type |
|------|------|
| Read employees data from dataset | TextFileInput2 |
| Write to Azure a Parquet file | ParquetFileOutput |

## Hops

| From | To |
|------|----|
| Read employees data from dataset | Write to Azure a Parquet file |
