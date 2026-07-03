# Pipeline: 00200-xls-loop

## Basic Information

- **Pipeline Name:** 00200-xls-loop
- **Source File:** `03-转换插件/输入类/samples/00200-xls-loop.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Pipeline executor | PipelineExecutor |

## Hops

| From | To |
|------|----|
| Generate rows | Pipeline executor |
