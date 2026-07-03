# Pipeline: 0004-check-variable

## Basic Information

- **Pipeline Name:** 0004-check-variable
- **Source File:** `03-转换插件/计算与字段操作类/samples/0004-check-variable.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| TEST4_PARAMETER1 |  | test4 parameter1 |
| TEST4_PARAMETER2 | default_test4_parameter2 | test4 parameter1 |

## Transforms

| Name | Type |
|------|------|
| Get variables | GetVariable |
| Pipeline Variables | WriteToLog |

## Hops

| From | To |
|------|----|
| Get variables | Pipeline Variables |
