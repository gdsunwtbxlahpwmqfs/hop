# Pipeline: 0101-create-ods-file

## Basic Information

- **Pipeline Name:** 0101-create-ods-file
- **Source File:** `03-转换插件/输出类/samples/0101-create-ods-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Write test ODS file | TypeExitExcelWriterTransform |

## Hops

| From | To |
|------|----|
| Data grid | Write test ODS file |
