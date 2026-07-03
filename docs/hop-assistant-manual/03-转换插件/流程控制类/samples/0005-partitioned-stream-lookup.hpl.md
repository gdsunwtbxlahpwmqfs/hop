# Pipeline: 0005-partitioned-stream-lookup

## Basic Information

- **Pipeline Name:** 0005-partitioned-stream-lookup
- **Source File:** `03-转换插件/流程控制类/samples/0005-partitioned-stream-lookup.hpl`

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
| partitioned | WriteToLog |

## Hops

| From | To |
|------|----|
| 10 rows | id |
| id | lookup value |
| Data grid | lookup |
| lookup | lookup value |
| lookup value | partitioned |
