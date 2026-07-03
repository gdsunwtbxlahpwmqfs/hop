# Pipeline: 0010-get-file-names-count-files

## Basic Information

- **Pipeline Name:** 0010-get-file-names-count-files
- **Source File:** `03-转换插件/Beam大数据类/samples/0010-get-file-names-count-files.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0010/count.txt | BeamOutput |
| /tmp/0010/test-file-*.txt | GetFileNames |
| filesCount | MemoryGroupBy |
| group | Constant |

## Hops

| From | To |
|------|----|
| filesCount | /tmp/0010/count.txt |
| /tmp/0010/test-file-*.txt | group |
| group | filesCount |
