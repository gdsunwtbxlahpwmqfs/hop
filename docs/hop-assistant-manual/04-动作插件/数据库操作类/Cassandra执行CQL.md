# Cassandra执行CQL（Cassandra Exec CQL）

## 功能概述


`Cassandra Exec CQL` 动作在 Cassandra 集群上执行一个或多个 Cassandra 查询语言（CQL）语句。
您可以使用它来创建或删除键空间、表、索引等。
如果所有语句成功执行，结果将为 true（成功）。
查看 [Cassandra 文档](https://cassandra.apache.org/doc/stable/cassandra/cql/) 了解更多关于 CQL 的信息。

## 主要选项

| 选项 | 说明 |
|------|------|
| Cassandra 连接（Cassandra Connection） | 您可以指定要使用的连接名称，可以是固定值或变量表达式。输入字段右侧有按钮帮助您管理元数据。 |
