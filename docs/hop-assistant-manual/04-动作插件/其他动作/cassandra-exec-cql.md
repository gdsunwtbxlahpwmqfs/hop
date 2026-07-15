# Cassandra Exec CQL

`Cassandra Exec CQL` action 在 Cassandra 集群上执行一条或多条 Cassandra 查询语言（CQL）语句。

您可以使用它来创建或删除键空间、表、索引等。

如果所有语句都成功执行，结果将为 true（成功）。

有关 CQL 的更多信息，请查阅 [Cassandra 文档](https://cassandra.apache.org/doc/stable/cassandra/cql/)。

## 选项

| 选项 | 描述 |
|---|---|
| [Cassandra Connection](../../06-元数据类型/cassandra-connection.md) | 您可以指定要使用的连接名称，可以是固定值或变量表达式。 |
| CQL 脚本 | 您可以输入一条或多条以新行上的分号结尾的 CQL 语句。 |
| 替换脚本中的变量 | 勾选此选项可在 CQL 脚本中添加变量。在将 CQL 脚本发送到 Cassandra 集群之前，这些变量将被替换为其变量值。 |

## 示例：

```
CREATE KEYSPACE IF NOT EXISTS hop
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3}
;
```
## 结果行

可以在此 action 中执行一个或多个查询。查询的输出将被添加到 action 的结果行中。您可以使用 [Get Rows From Result](../../03-转换插件/其他转换/getrowsfromresult.md) transform 等 plugin 或 [JavaScript](../数据库操作类/eval.md) action 来访问这些结果行。
请注意，结果行保存在内存中，因此仅适用于小型查询，例如从 `system_schema` 表获取信息。
