# Pipeline: 0070-excel-input-formula-3670

## Basic Information

- **Pipeline Name:** 0070-excel-input-formula-3670
- **Source File:** `03-转换插件/输入类/samples/0070-excel-input-formula-3670.hpl`

## Transforms

| Name | Type |
|------|------|
| preview | Dummy |
| read xlsx with formulas | ExcelInput |

## Hops

| From | To |
|------|----|
| read xlsx with formulas | preview |
