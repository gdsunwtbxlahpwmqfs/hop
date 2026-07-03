# Pipeline: 0053-excel-input-xls-basic

## Basic Information

- **Pipeline Name:** 0053-excel-input-xls-basic
- **Source File:** `03-转换插件/输入类/samples/0053-excel-input-xls-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| basic.xls | ExcelInput |

## Hops

| From | To |
|------|----|
| basic.xls | Verify |
