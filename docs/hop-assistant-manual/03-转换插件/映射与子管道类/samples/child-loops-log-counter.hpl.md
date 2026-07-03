# Pipeline: loops-log-counter

## Basic Information

- **Pipeline Name:** loops-log-counter
- **Source File:** `03-转换插件/映射与子管道类/samples/child-loops-log-counter.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PRM_COUNTER |  | receive a counter parameter and print the value to the log. |

## Transforms

| Name | Type |
|------|------|
| generate 1 row | RowGenerator |
| log ${PRM_COUNTER} | WriteToLog |
| Copy rows to result | RowsToResult |

## Hops

| From | To |
|------|----|
| generate 1 row | log ${PRM_COUNTER} |
| log ${PRM_COUNTER} | Copy rows to result |
