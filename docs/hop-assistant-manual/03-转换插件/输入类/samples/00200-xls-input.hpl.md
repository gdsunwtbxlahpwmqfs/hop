# Pipeline: 00200-xls-input

## Basic Information

- **Pipeline Name:** 00200-xls-input
- **Source File:** `03-转换插件/输入类/samples/00200-xls-input.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Microsoft Excel input | ExcelInput |

## Hops

| From | To |
|------|----|
| Microsoft Excel input | Dummy (do nothing) |
