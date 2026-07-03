# Pipeline: copy_rows

## Basic Information

- **Pipeline Name:** copy_rows
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-actionpipeline-preparerows.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Copy rows to result | RowsToResult |
| Start | WriteToLog |

## Hops

| From | To |
|------|----|
| Data grid | Start |
| Start | Copy rows to result |
