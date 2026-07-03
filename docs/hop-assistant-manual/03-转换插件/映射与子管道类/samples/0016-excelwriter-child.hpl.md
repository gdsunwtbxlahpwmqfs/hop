# Pipeline: 0016-excelwriter-child

## Basic Information

- **Pipeline Name:** 0016-excelwriter-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0016-excelwriter-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Microsoft Excel writer | TypeExitExcelWriterTransform |

## Hops

| From | To |
|------|----|
| Generate rows | Microsoft Excel writer |
