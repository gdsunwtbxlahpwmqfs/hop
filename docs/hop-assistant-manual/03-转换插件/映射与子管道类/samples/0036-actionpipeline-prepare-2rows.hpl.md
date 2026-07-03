# Pipeline: 0036-actionpipeline-prepare-2rows

## Basic Information

- **Pipeline Name:** 0036-actionpipeline-prepare-2rows
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-actionpipeline-prepare-2rows.hpl`

## Transforms

| Name | Type |
|------|------|
| Copy rows to result | RowsToResult |
| Data grid | DataGrid |
| Generate rows | RowGenerator |
| Start | WriteToLog |

## Hops

| From | To |
|------|----|
| Data grid | Copy rows to result |
| Generate rows | Start |
| Start | Data grid |
