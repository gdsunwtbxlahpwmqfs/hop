# Beam Kafka 生产（Beam Kafka Produce）

Beam Kafka 生产转换使用 Beam 执行引擎向 Kafka 集群发布（写入）记录。该转换专为在 Beam 引擎上运行的管道设计。

## 局限性

Kafka 生产者的主要局限在于：当前仅支持以字符串（String）作为键，以字符串或 Avro 记录（Avro Record）作为值进行写入或生产。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ❌ 不支持 |
| Spark | ✅ 支持 |
| Flink | ✅ 支持 |
| Dataflow | ✅ 支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 转换的名称，在单个管道中必须唯一。 |
| 引导服务器（Bootstrap servers） | 以逗号分隔的"引导"Kafka 集群中 Kafka 代理（broker）主机列表。 |
| 主题（The topics） | 要发布到的主题。 |
| 用作键的字段（The field to use as key） | 记录的键。 |
| 用作消息的字段（The field to use as message） | 记录的消息内容。 |

### Avro 与模式注册表（Schema Registry）

向 Kafka 服务器发送 Avro 记录值时，Avro 值的模式不会发送到 Kafka，而是发送到模式注册表（schema registry），因此您需要准备一个可用的注册表。以下是在 Confluent Cloud Kafka 实例上使其正常工作所需的部分选项。由于软件栈中有多个部分需要身份验证，因此存在一些冗余。建议将这些选项放入环境配置文件的变量中。

| 选项 | 示例 |
|------|------|
| schema.registry.url | https://abcd-12345x.europe-west3.gcp.confluent.cloud |
| value.converter.schema.registry.url | https://abcd-12345x.europe-west3.gcp.confluent.cloud |
| auto.register.schemas | true |
| security.protocol | SASL_SSL |
| sasl.jaas.config | org.apache.kafka.common.security.plain.PlainLoginModule required username="CLUSTER_API_KEY" password="CLUSTER_API_SECRET"; |
