# Pipeline: 00100-excelinput

## Basic Information

- **Pipeline Name:** 00100-excelinput
- **Source File:** `03-转换插件/输入类/samples/00100-excelinput.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy ODS | Dummy |
| Dummy XLS | Dummy |
| Dummy XLSX | Dummy |
| Dummy XLSX Streaming | Dummy |
| Microsoft Excel input ODS | ExcelInput |
| Microsoft Excel input XLS | ExcelInput |
| Microsoft Excel input XLSX | ExcelInput |
| Microsoft Excel input XLSX Streaming | ExcelInput |

## Hops

| From | To |
|------|----|
| Microsoft Excel input XLS | Dummy XLS |
| Microsoft Excel input XLSX | Dummy XLSX |
| Microsoft Excel input XLSX Streaming | Dummy XLSX Streaming |
| Microsoft Excel input ODS | Dummy ODS |
