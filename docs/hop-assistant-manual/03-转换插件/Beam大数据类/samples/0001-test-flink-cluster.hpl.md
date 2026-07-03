# Pipeline: 0001-test-flink-cluster

## Basic Information

- **Pipeline Name:** 0001-test-flink-cluster
- **Source File:** `03-转换插件/Beam大数据类/samples/0001-test-flink-cluster.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| Generate rows | Dummy (do nothing) |
