# Cassandra 输入

Cassandra 输入（Cassandra Input）转换使用 CQL（Cassandra Query Language）3.x 版本从 Apache Cassandra 集群的 Cassandra 表中读取数据。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| Cassandra 连接 | 到 Cassandra 集群的连接配置 |
| CQL 查询 | 用于检索数据的 CQL 语句 |
| 键空间 | 目标键空间 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 使用 CQL 3.x 版本查询 Cassandra 数据库。
- 可在 CQL 查询中使用 Hop 变量（如 `${变量名}`）实现参数化查询。
