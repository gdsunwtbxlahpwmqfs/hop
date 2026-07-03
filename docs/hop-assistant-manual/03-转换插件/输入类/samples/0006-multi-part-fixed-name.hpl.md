# Pipeline: 0006-multi-part-fixed-name

## Basic Information

- **Pipeline Name:** 0006-multi-part-fixed-name
- **Source File:** `03-转换插件/输入类/samples/0006-multi-part-fixed-name.hpl`

## Transforms

| Name | Type |
|------|------|
| Add sequence | Sequence |
| Generate rows | RowGenerator |
| Microsoft Excel writer | TypeExitExcelWriterTransform |

## Hops

| From | To |
|------|----|
| Generate rows | Add sequence |
| Add sequence | Microsoft Excel writer |
