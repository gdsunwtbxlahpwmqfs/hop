# Pipeline: 0002-pipeline

## Basic Information

- **Pipeline Name:** 0002-pipeline
- **Source File:** `03-转换插件/计算与字段操作类/samples/0002-pipeline.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| TEST2_OTHER | other parameter | Other parameter |
| TEST2_PARAMETER | bad value | Test2 parameter |

## Transforms

| Name | Type |
|------|------|
| 0002-workflow.hwf | WorkflowExecutor |
| test-values | DataGrid |

## Hops

| From | To |
|------|----|
| test-values | 0002-workflow.hwf |
