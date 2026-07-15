# Avro

来自 [avro.apache.org](https://avro.apache.org)：

Apache Avro™ 是一个数据序列化系统。要了解有关 Avro 的更多信息，请阅读[当前文档](https://avro.apache.org/docs/current/)。

Hop 通过多个 plugin 支持 Avro。
首先，我们将 Avro 支持为一种名为"Avro Record"的数据类型。它简单地描述了一个类型为 `org.apache.avro.generic.GenericRecord` 的类。这反过来封装了 Avro schema 和一组值。

## Pipeline Transform

- [Avro Decode](pipeline/transforms/avro-decode.md)：允许您从 Avro Record 数据类型中提取 Hop 值。
- [Avro Encode](pipeline/transforms/avro-encode.md)：允许您从 Avro Record 数据类型中提取 Hop 值。

## Kafka Producer transform

如果您想使用 Hop Kafka transform 发送或接收 Avro 记录，设置以下选项很重要：

| 选项 | 描述 | 示例 |
|---|---|---|
| `value.converter.schema.registry.url` |  |  |
| 指定您的 Confluent schema registry 的 URL。 |  |  |
| http://confluent-server:8081 |  |  |
| `schema.registry.url` |  |  |
| 指定您的 Confluent schema registry 的 URL。 |  |  |
| http://confluent-server:8081 |  |  |
| `auto.register.schemas` |  |  |
| 指示 Kafka 在 Confluent schema registry 中自动注册 Avro 记录的 schema。 |  |  |
| true |  |  |
