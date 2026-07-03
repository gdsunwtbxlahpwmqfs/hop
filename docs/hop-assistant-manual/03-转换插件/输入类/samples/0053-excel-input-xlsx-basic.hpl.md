# Pipeline: 0053-excel-input-xlsx-basic

## Basic Information

- **Pipeline Name:** 0053-excel-input-xlsx-basic
- **Source File:** `03-转换插件/输入类/samples/0053-excel-input-xlsx-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| basic.xlsx | ExcelInput |

## Hops

| From | To |
|------|----|
| basic.xlsx | Verify |
