# Pipeline: 0038-getfilenames-regexp

## Basic Information

- **Pipeline Name:** 0038-getfilenames-regexp
- **Source File:** `03-转换插件/输入类/samples/0038-getfilenames-regexp.hpl`

## Transforms

| Name | Type |
|------|------|
| Check num rows | FilterRows |
| Count rows | GroupBy |
| Get file names | GetFileNames |
| No files found - Abort | Abort |
| Success | Dummy |

## Hops

| From | To |
|------|----|
| Get file names | Count rows |
| Count rows | Check num rows |
| Check num rows | No files found - Abort |
| Check num rows | Success |
