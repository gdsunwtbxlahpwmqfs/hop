# Pipeline: 0010-write-google-sheet

## Basic Information

- **Pipeline Name:** 0010-write-google-sheet
- **Source File:** `03-转换插件/输入类/samples/0011-read-multi-sheet-regex.hpl`

## Transforms

| Name | Type |
|------|------|
| Microsoft Excel input | ExcelInput |
| Memory group by | MemoryGroupBy |
| Filter rows | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Microsoft Excel input | Memory group by |
| Memory group by | Filter rows |
| Filter rows | Abort |
