# Pipeline: 0029-parquet-input-no-files

## Basic Information

- **Pipeline Name:** 0029-parquet-input-no-files
- **Source File:** `03-转换插件/输入类/samples/0029-parquet-input-no-files.hpl`

## Transforms

| Name | Type |
|------|------|
| Count | GroupBy |
| Detect empty stream | DetectEmptyStream |
| No filenames | DataGrid |
| Parquet File Input | ParquetFileInput |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| No filenames | Parquet File Input |
| Parquet File Input | Detect empty stream |
| Detect empty stream | Count |
| Count | Output |
