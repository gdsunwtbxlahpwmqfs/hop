# Pipeline: 0003-child-pipeline

## Basic Information

- **Pipeline Name:** 0003-child-pipeline
- **Source File:** `03-转换插件/计算与字段操作类/samples/0003-child-pipeline.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| TEST3_PARAMETER1 |  | test3 parameter1 without default |
| TEST3_PARAMETER2 | default2 | test3 parameter2 with default |

## Transforms

| Name | Type |
|------|------|
| Get parameter values | GetVariable |
| Log parameters | WriteToLog |

## Hops

| From | To |
|------|----|
| Get parameter values | Log parameters |
