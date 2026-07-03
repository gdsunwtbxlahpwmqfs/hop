# Pipeline: 0037-getfilenames-basic

## Basic Information

- **Pipeline Name:** 0037-getfilenames-basic
- **Source File:** `03-转换插件/输入类/samples/0038-getfilenames-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Get file names | GetFileNames |
| Count rows | GroupBy |
| Check num rows | FilterRows |
| No files found - Abort | Abort |
| Check filename | FilterRows |
| Wrong file found - Abort | Abort |
| Success | Dummy |

## Hops

| From | To |
|------|----|
| Get file names | Count rows |
| Count rows | Check num rows |
| Check num rows | No files found - Abort |
| Check filename | Wrong file found - Abort |
| Check num rows | Check filename |
| Check filename | Success |
