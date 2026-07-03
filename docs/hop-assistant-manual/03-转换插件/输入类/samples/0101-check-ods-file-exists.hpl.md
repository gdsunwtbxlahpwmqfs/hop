# Pipeline: 0101-check-ods-file-exists

## Basic Information

- **Pipeline Name:** 0101-check-ods-file-exists
- **Source File:** `03-转换插件/输入类/samples/0101-check-ods-file-exists.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort because expected file not found! | Abort |
| Cleanup temporary file | ProcessFiles |
| Detect empty stream | DetectEmptyStream |
| File exists! | Dummy |
| Look for test ODS file | GetFileNames |

## Hops

| From | To |
|------|----|
| File exists! | Cleanup temporary file |
| Look for test ODS file | Detect empty stream |
| Detect empty stream | Abort because expected file not found! |
| Look for test ODS file | File exists! |
