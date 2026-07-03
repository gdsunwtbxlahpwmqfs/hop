# Pipeline: 0004-test-header

## Basic Information

- **Pipeline Name:** 0004-test-header
- **Source File:** `03-转换插件/输入类/samples/0004-test-header.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Microsoft Excel writer | TypeExitExcelWriterTransform |
| remove empty line | FilterRows |

## Hops

| From | To |
|------|----|
| Data grid | remove empty line |
| remove empty line | Microsoft Excel writer |
