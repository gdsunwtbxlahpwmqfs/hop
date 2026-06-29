# MongoDB 输出

MongoDB 输出（MongoDB Output）转换可将数据输出到 MongoDB 数据库[集合（collection）](http://docs.mongodb.org/manual/reference/glossary/)。

有关 MongoDB 的更多信息，请参见 MongoDB [文档](http://www.mongodb.org/)。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| MongoDB 连接 | 到 MongoDB 数据库的连接配置 |
| 数据库 | 目标数据库名称 |
| 集合 | 要写入的集合名称 |
| 字段映射 | 输入字段到 MongoDB 文档字段的映射 |
| 操作 | 插入、更新、插入或更新等操作 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 支持多种写操作，包括插入、更新和插入或更新。
- 字段映射将输入行转换为 MongoDB 文档结构。
