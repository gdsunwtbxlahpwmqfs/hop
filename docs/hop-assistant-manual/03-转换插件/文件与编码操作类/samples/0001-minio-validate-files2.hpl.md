# Pipeline: 0001-minio-validate-files2

## Basic Information

- **Pipeline Name:** 0001-minio-validate-files2
- **Source File:** `03-转换插件/文件与编码操作类/samples/0001-minio-validate-files2.hpl`

## Transforms

| Name | Type |
|------|------|
| Validate | Dummy |
| last modified date is after 2024 | FilterRows |
| list minio:///demo/copy | GetFileNames |

## Hops

| From | To |
|------|----|
| last modified date is after 2024 | Validate |
| list minio:///demo/copy | last modified date is after 2024 |
