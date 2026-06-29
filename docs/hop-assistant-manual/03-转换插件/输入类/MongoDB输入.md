# MongoDB 输入

MongoDB 输入（MongoDB Input）转换使你能够从 MongoDB 集合中检索文档或记录。

有关 MongoDB 的更多信息，请参见 MongoDB [文档](http://www.mongodb.org/)。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| MongoDB 连接 | 到 MongoDB 数据库的连接配置 |
| 数据库 | 目标数据库名称 |
| 集合 | 要查询的集合名称 |
| 查询 | MongoDB 查询表达式（JSON 格式） |
| 字段 | 输出字段定义 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 查询使用 MongoDB 的 JSON 查询语法。
- 适用于从 NoSQL 文档数据库中检索数据。
