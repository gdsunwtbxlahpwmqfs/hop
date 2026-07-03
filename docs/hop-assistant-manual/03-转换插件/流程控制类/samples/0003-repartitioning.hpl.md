# Pipeline: 0003-repartitioning

## Basic Information

- **Pipeline Name:** 0003-repartitioning
- **Source File:** `03-转换插件/流程控制类/samples/0003-repartitioning.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| NR_PARTITIONS |  | Number of dynamic partitions |

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| id | Sequence |
| partitioned | WriteToLog |
| repartitioned | WriteToLog |

## Hops

| From | To |
|------|----|
| 10 rows | id |
| id | partitioned |
| partitioned | repartitioned |
