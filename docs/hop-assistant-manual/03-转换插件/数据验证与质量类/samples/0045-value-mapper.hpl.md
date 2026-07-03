# Pipeline: 0045-value-mapper

## Basic Information

- **Pipeline Name:** 0045-value-mapper
- **Source File:** `03-转换插件/数据验证与质量类/samples/0045-value-mapper.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| NOT_DEFINED | Not defined |  |
| NOT_FOUND | Not found |  |

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Dummy | Dummy |
| Value mapper | ValueMapper |

## Hops

| From | To |
|------|----|
| Data grid | Value mapper |
| Value mapper | Dummy |
