# Pipeline: 0036-actionpipeline-getresults-fromparams

## Basic Information

- **Pipeline Name:** 0036-actionpipeline-getresults-fromparams
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-actionpipeline-getresults-fromparams.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| p1 |  |  |

## Transforms

| Name | Type |
|------|------|
| Abort 1 | Abort |
| Abort 2 | Abort |
| Check field value | FilterRows |
| Check if there's a value | FilterRows |
| Get variables | GetVariable |
| If Null | IfNull |
| Success | Dummy |

## Hops

| From | To |
|------|----|
| Get variables | If Null |
| If Null | Check if there's a value |
| Check if there's a value | Abort 1 |
| Check if there's a value | Check field value |
| Check field value | Success |
| Check field value | Abort 2 |
