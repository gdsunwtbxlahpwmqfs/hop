# Pipeline: 0055-file-metadata-filename-fromfield-verify

## Basic Information

- **Pipeline Name:** 0055-file-metadata-filename-fromfield-verify
- **Source File:** `03-转换插件/文件与编码操作类/samples/0055-file-metadata-filename-fromfield-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate filename | Calculator |
| Generate rows | RowGenerator |
| Get PROJECT_HOME | GetVariable |
| Verify | Dummy |
| files/customers-100.txt | FileMetadataPlugin |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | Verify |
| Generate rows | Get PROJECT_HOME |
| Get PROJECT_HOME | Generate filename |
| Generate filename | files/customers-100.txt |
