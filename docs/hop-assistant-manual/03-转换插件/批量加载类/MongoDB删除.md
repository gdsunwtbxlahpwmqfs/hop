# MongoDB 删除

MongoDB 删除（MongoDB Delete）转换使你能够从 MongoDB 集合中删除文档或记录。

有关 MongoDB 的更多信息，请参见 MongoDB [文档](http://www.mongodb.org/)。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| MongoDB 连接 | 到 MongoDB 数据库的连接配置 |
| 数据库 | 目标数据库名称 |
| 集合 | 要删除数据的集合名称 |
| 删除条件 | 用于匹配要删除文档的查询条件 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 删除条件使用 MongoDB 的 JSON 查询语法匹配文档。
- 删除操作不可逆，请谨慎使用。
