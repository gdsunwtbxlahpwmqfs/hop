# Pipeline: 0001-add-sequence-copy-safe-child

## Basic Information

- **Pipeline Name:** 0001-add-sequence-copy-safe-child
- **Source File:** `03-转换插件/计算与字段操作类/samples/0001-add-sequence-copy-safe-child.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PARAM_INCREMENT_VALUE |  |  |
| PARAM_INSTANCE |  |  |
| PARAM_START_VALUE |  |  |

## Transforms

| Name | Type |
|------|------|
| Add sequence | Sequence |
| Generate rows | RowGenerator |
| Get PARAMs | GetVariable |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Generate rows | Get PARAMs |
| Get PARAMs | Add sequence |
| Add sequence | Write to log |
