# Pipeline: 0003-pipeline-parent

## Basic Information

- **Pipeline Name:** 0003-pipeline-parent
- **Source File:** `03-转换插件/计算与字段操作类/samples/0003-pipeline-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0003-child-pipeline.hpl | PipelineExecutor |
| test-values | DataGrid |

## Hops

| From | To |
|------|----|
| test-values | 0003-child-pipeline.hpl |
