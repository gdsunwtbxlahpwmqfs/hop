# Pipeline: 0006-check-variables

## Basic Information

- **Pipeline Name:** 0006-check-variables
- **Source File:** `03-转换插件/计算与字段操作类/samples/0006-check-variables.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| TEST6_PARAMETER1 |  | test6 parameter1 |
| TEST6_PARAMETER2 | default_test6_parameter2 | test6 parameter1 |

## Transforms

| Name | Type |
|------|------|
| Get variables | GetVariable |
| Pipeline Variables | WriteToLog |

## Hops

| From | To |
|------|----|
| Get variables | Pipeline Variables |
