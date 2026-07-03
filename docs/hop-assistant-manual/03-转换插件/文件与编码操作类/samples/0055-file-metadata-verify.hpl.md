# Pipeline: 0055-file-metadata-verify

## Basic Information

- **Pipeline Name:** 0055-file-metadata-verify
- **Source File:** `03-转换插件/文件与编码操作类/samples/0055-file-metadata-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| files/customers-100.txt | FileMetadataPlugin |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | Verify |
