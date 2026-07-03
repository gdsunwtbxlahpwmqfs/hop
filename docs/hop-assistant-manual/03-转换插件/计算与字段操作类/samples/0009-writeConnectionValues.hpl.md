# Pipeline: writeConnectionValues

## Basic Information

- **Pipeline Name:** writeConnectionValues
- **Source File:** `03-转换插件/计算与字段操作类/samples/0009-writeConnectionValues.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| dbName |  |  |
| environment |  |  |
| port |  |  |
| pw |  |  |
| tenant |  |  |
| type |  |  |
| url |  |  |
| username |  |  |

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Get variables | GetVariable |
| Write to log | WriteToLog |
| not null url | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Generate rows | Get variables |
| Get variables | not null url |
| not null url | Write to log |
| not null url | Abort |
