# Pipeline: 0002-dynamic-partitioning.hpl

## Basic Information

- **Pipeline Name:** 0002-dynamic-partitioning.hpl
- **Source File:** `03-转换插件/流程控制类/samples/0002-dynamic-partitioning.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| NR_PARTITIONS |  | Number of partitions |

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| id | Sequence |
| partitioned | WriteToLog |

## Hops

| From | To |
|------|----|
| 10 rows | id |
| id | partitioned |
