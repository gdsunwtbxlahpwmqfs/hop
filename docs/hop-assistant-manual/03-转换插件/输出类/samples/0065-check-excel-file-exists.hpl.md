# Pipeline: 0064-check-excel-file-exists.hpl

## Basic Information

- **Pipeline Name:** 0064-check-excel-file-exists.hpl
- **Source File:** `03-转换插件/输出类/samples/0065-check-excel-file-exists.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort because expected file not found! | Abort |
| Cleanup temporary file | ProcessFiles |
| Detect empty stream | DetectEmptyStream |
| File exists! | Dummy |
| Look for test file in target dir | GetFileNames |

## Hops

| From | To |
|------|----|
| File exists! | Cleanup temporary file |
| Look for test file in target dir | Detect empty stream |
| Detect empty stream | Abort because expected file not found! |
| Look for test file in target dir | File exists! |
