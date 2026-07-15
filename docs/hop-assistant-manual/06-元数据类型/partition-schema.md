# Partition Schema

## 描述

![](../assets/images/icons/partition_schema.svg)

描述一个分区 schema。
分区 schema 定义了行流将被拆分的方式数量。
用于分区的名称可以是任何你喜欢的名称。

## 相关 plugin

无/全部

## 选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Partition schema name |  | 此分区 schema 的名称 |
| Dynamically create the schema definition | ? |  |
| Number of partitions? | 4 |  |
| Partitions |  | 分区 ID 列表 |

## 示例（集成测试）

- integration-tests/partitioning/0006-partitioned-when-stream-lookup-should-fail2.hpl
- integration-tests/partitioning/0004-copies-repartitioning.hpl
- integration-tests/partitioning/0001-static-partitioning.hpl
- integration-tests/partitioning/0005-partitioned-stream-lookup.hpl
- integration-tests/partitioning/0006-partitioned-when-stream-lookup-should-fail.hpl
- integration-tests/partitioning/0003-repartitioning.hpl
- integration-tests/partitioning/0005-non-partitioned-stream-lookup.hpl
- integration-tests/partitioning/0002-dynamic-partitioning.hpl
