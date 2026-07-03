# Pipeline: 0025-get-variables-child

## Basic Information

- **Pipeline Name:** 0025-get-variables-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0025-get-variables-child.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| A | aaa |  |
| B | bbb |  |
| C | ccc |  |

## Transforms

| Name | Type |
|------|------|
| Get variables | GetVariable |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Get variables | Output |
