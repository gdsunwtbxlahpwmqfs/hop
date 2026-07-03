# Pipeline: 0005-non-partitioned-stream-lookup

## Basic Information

- **Pipeline Name:** 0005-non-partitioned-stream-lookup
- **Source File:** `03-转换插件/流程控制类/samples/0005-non-partitioned-stream-lookup.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| NR_PARTITIONS | 3 | Number of dynamic partitions |

## Transforms

| Name | Type |
|------|------|
| 10M rows | RowGenerator |
| Collect | Dummy |
| Data grid | DataGrid |
| id | Sequence |
| lookup | Dummy |
| lookup value | StreamLookup |

## Hops

| From | To |
|------|----|
| 10M rows | id |
| id | lookup value |
| Data grid | lookup |
| lookup | lookup value |
| lookup value | Collect |
