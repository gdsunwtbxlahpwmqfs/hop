# Pipeline: 0007-append-to-xlsx

## Basic Information

- **Pipeline Name:** 0007-append-to-xlsx
- **Source File:** `03-转换插件/输入类/samples/0007-append-to-xlsx.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Microsoft Excel writer | TypeExitExcelWriterTransform |

## Hops

| From | To |
|------|----|
| Data grid | Microsoft Excel writer |
