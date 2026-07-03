# Pipeline: pipeline-with-parameter

## Basic Information

- **Pipeline Name:** pipeline-with-parameter
- **Source File:** `03-转换插件/samples/pipeline-with-parameter.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PRM_EXAMPLE |  |  |

## Transforms

| Name | Type |
|------|------|
| get ${PRM_EXAMPLE} | GetVariable |
| write parameter to log | WriteToLog |
| get ${ENV_VARIABLE} | GetVariable |
| write env_variable to log | WriteToLog |

## Hops

| From | To |
|------|----|
| get ${PRM_EXAMPLE} | write parameter to log |
| write parameter to log | get ${ENV_VARIABLE} |
| get ${ENV_VARIABLE} | write env_variable to log |
