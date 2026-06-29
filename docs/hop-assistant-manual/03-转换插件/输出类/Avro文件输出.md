# Avro 文件输出

Avro 文件输出（Avro File Output）转换可将 Apache Avro 消息写入文件或字段。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 输出文件 | 输出的 Avro 文件名 |
| Schema | Avro 模式定义 |
| 字段 | 输出字段映射 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- Avro 是一种数据序列化系统，常用于 Hadoop 生态中的大数据存储。
- 可将数据写入 Avro 文件或作为字段输出。
