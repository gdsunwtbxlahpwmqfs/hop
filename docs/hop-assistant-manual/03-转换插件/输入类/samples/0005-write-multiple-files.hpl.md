# Pipeline: 0005-write-multiple-files

## Basic Information

- **Pipeline Name:** 0005-write-multiple-files
- **Source File:** `03-转换插件/输入类/samples/0005-write-multiple-files.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Microsoft Excel writer | TypeExitExcelWriterTransform |

## Hops

| From | To |
|------|----|
| Data grid | Microsoft Excel writer |
