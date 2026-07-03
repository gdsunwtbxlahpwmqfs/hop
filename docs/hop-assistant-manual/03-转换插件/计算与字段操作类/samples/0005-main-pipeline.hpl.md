# Pipeline: 0005-main-pipeline

## Basic Information

- **Pipeline Name:** 0005-main-pipeline
- **Source File:** `03-转换插件/计算与字段操作类/samples/0005-main-pipeline.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| TEST5_PARAMETER1 |  | Test5 parameter1 |
| TEST5_PARAMETER2 | test5_default5 | Test5 parameter2 |

## Transforms

| Name | Type |
|------|------|
| 1 row | RowGenerator |
| 00005-mapping.hpl | SimpleMapping |

## Hops

| From | To |
|------|----|
| 1 row | 00005-mapping.hpl |
