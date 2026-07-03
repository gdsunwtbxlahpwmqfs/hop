# Pipeline: 0006-partitioned-when-stream-lookup-should-fail2

## Basic Information

- **Pipeline Name:** 0006-partitioned-when-stream-lookup-should-fail2
- **Source File:** `03-转换插件/流程控制类/samples/0006-partitioned-when-stream-lookup-should-fail2.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| NR_PARTITIONS | 2 | Number of dynamic partitions |

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| Data grid | DataGrid |
| id | Sequence |
| lookup | Dummy |
| lookup value | StreamLookup |

## Hops

| From | To |
|------|----|
| 10 rows | id |
| id | lookup value |
| Data grid | lookup |
| lookup | lookup value |
