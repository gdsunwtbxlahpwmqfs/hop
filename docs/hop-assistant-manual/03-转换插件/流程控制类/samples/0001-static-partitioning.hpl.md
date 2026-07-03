# Pipeline: 0001-static-partitioning

## Basic Information

- **Pipeline Name:** 0001-static-partitioning
- **Source File:** `03-转换插件/流程控制类/samples/0001-static-partitioning.hpl`

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
