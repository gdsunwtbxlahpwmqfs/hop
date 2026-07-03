# Pipeline: 0010-get-file-names-create-files

## Basic Information

- **Pipeline Name:** 0010-get-file-names-create-files
- **Source File:** `03-转换插件/Beam大数据类/samples/0010-get-file-names-create-files.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/test-file-*.txt | TextFileOutput |
| 10000 rows | RowGenerator |
| filename | ScriptValueMod |
| nr | Sequence |
| tmpFolder | GetVariable |

## Hops

| From | To |
|------|----|
| 10000 rows | nr |
| nr | tmpFolder |
| tmpFolder | filename |
| filename | /tmp/test-file-*.txt |
