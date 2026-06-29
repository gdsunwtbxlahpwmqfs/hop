# Avro 文件输入

Avro 文件输入转换用于从一个或多个文件中读取 Avro 记录。每条记录封装在一个 Avro 字段中，每个值都有其自己的 Schema 和记录。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 文件名 | 要读取的 Avro 文件 |
| Schema | Avro 模式定义 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 每条记录都封装在 Avro 字段中，每个值拥有独立的 Schema 和记录定义。
- Avro 是一种数据序列化系统，适用于 Hadoop 生态中的数据存储与交换。
