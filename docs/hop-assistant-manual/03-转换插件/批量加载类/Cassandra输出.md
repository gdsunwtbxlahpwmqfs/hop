# Cassandra 输出

Cassandra 输出（Cassandra Output）转换使用 CQL（Cassandra Query Language）3.x 版本将数据写入 Apache Cassandra 集群的 Cassandra 表。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| Cassandra 连接 | 到 Cassandra 集群的连接配置 |
| 键空间 | 目标键空间 |
| 目标表 | 要写入的 Cassandra 表名 |
| 字段映射 | 输入字段到 Cassandra 列的映射 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 使用 CQL 3.x 写入 Cassandra 数据库。
- 可在配置中使用 Hop 变量（如 `${变量名}`）。
