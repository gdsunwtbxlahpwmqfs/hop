# Pipeline: 00005-mapping

## Basic Information

- **Pipeline Name:** 00005-mapping
- **Source File:** `03-转换插件/计算与字段操作类/samples/00005-mapping.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| TEST5_PARAMETER1 |  | Test5 parameter1 |
| TEST5_PARAMETER2 | test5_default5 | Test5 parameter2 |

## Transforms

| Name | Type |
|------|------|
| Get variables | GetVariable |
| Mapping Input | MappingInput |
| Mapping Output | MappingOutput |
| Mapping Variables | WriteToLog |

## Hops

| From | To |
|------|----|
| Mapping Input | Get variables |
| Get variables | Mapping Variables |
| Mapping Variables | Mapping Output |
