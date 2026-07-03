# Pipeline: 0053-excel-input-ods-basic

## Basic Information

- **Pipeline Name:** 0053-excel-input-ods-basic
- **Source File:** `03-转换插件/输入类/samples/0053-excel-input-ods-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| basic.ods | ExcelInput |

## Hops

| From | To |
|------|----|
| basic.ods | Verify |
