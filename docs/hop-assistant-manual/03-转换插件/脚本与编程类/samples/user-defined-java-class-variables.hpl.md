# Pipeline: user-defined-java-class-variables

## Basic Information

- **Pipeline Name:** user-defined-java-class-variables
- **Source File:** `03-转换插件/脚本与编程类/samples/user-defined-java-class-variables.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| SAMPLE_PARAMETER | abcd | Just a variable that gets set with default value abcd |

## Transforms

| Name | Type |
|------|------|
| 10M rows | RowGenerator |
| UDJC: get variable sample | UserDefinedJavaClass |

## Hops

| From | To |
|------|----|
| 10M rows | UDJC: get variable sample |
