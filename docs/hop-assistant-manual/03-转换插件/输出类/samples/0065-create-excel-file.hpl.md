# Pipeline: 0064-create-excel-file.hpl

## Basic Information

- **Pipeline Name:** 0064-create-excel-file.hpl
- **Source File:** `03-转换插件/输出类/samples/0065-create-excel-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Write test excel file | TypeExitExcelWriterTransform |

## Hops

| From | To |
|------|----|
| Data grid | Write test excel file |
