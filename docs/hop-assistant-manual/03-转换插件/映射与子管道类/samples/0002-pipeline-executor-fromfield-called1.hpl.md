# Pipeline: pipeline1

## Basic Information

- **Pipeline Name:** pipeline1
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-pipeline-executor-fromfield-called1.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Copy rows to result | RowsToResult |

## Hops

| From | To |
|------|----|
| Generate rows | Copy rows to result |
