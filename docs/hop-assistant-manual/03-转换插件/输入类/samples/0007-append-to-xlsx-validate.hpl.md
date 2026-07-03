# Pipeline: 0007-append-to-xlsx-validate

## Basic Information

- **Pipeline Name:** 0007-append-to-xlsx-validate
- **Source File:** `03-转换插件/输入类/samples/0007-append-to-xlsx-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Filter rows | FilterRows |
| Group by | GroupBy |
| Microsoft Excel input | ExcelInput |

## Hops

| From | To |
|------|----|
| Microsoft Excel input | Group by |
| Group by | Filter rows |
| Filter rows | Abort |
