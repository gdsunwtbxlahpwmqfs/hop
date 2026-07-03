# Pipeline: 0001-minio-create-files

## Basic Information

- **Pipeline Name:** 0001-minio-create-files
- **Source File:** `03-转换插件/文件与编码操作类/samples/0001-minio-create-files.hpl`

## Transforms

| Name | Type |
|------|------|
| 100 rows | RowGenerator |
| filename | ScriptValueMod |
| nr | Sequence |
| write to filename | TextFileOutput |

## Hops

| From | To |
|------|----|
| 100 rows | nr |
| filename | write to filename |
| nr | filename |
