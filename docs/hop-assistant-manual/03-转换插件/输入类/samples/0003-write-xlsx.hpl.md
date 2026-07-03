# Pipeline: 0003-write-xlsx

## Basic Information

- **Pipeline Name:** 0003-write-xlsx
- **Source File:** `03-转换插件/输入类/samples/0003-write-xlsx.hpl`

## Transforms

| Name | Type |
|------|------|
| Fake data | Fake |
| Generate rows | RowGenerator |
| Microsoft Excel writer | TypeExitExcelWriterTransform |

## Hops

| From | To |
|------|----|
| Generate rows | Fake data |
| Fake data | Microsoft Excel writer |
