# ![Beam Kafka Consume Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-kafka-input.svg) Beam Kafka Consume

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 限制

Kafka Consumer 的主要限制是当前仅支持以 String 作为键、以 String 和 Avro Record 作为值进行读取。

## 选项

| Option | Description |
|---|---|
| Transform name |  |
| Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |  |
| Bootstrap servers |  |
| 以逗号分隔的主机列表，这些主机是 "bootstrap" Kafka 集群中的 Kafka broker。 |  |
| Topics to consume |  |
| 以逗号分隔的要消费的主题列表。 |  |
| Group ID |  |
| 指定 Kafka consumer 所属的消费者组 ID。 |  |
| The name of the key output field |  |
| 结果键字段。 |  |
| The name of the message output field |  |
| 结果消息字段。 |  |
| The message type (default is Sting) |  |
| 从 Kafka 获取的消息类型。 |  |
| Schema registry URL (Avro) |  |
| 如果你正在消费 Avro Record 消息时的 schema registry URL |  |
| Schema registry subject (Avro) |  |
| schema registry 中 subject（schema 名称）的名称。这将使我们了解正在消费什么样的 Avro 值。 |  |
| Use processing time |  |
| 记录被 Beam 处理的时间。 |  |
| Use log append time |  |
| 记录被 broker 追加的时间。 |  |
| Use create time |  |
| 生产者记录创建的时间。 |  |
| Restrict read to committed messages |  |
| 仅限制读取已提交的记录。 |  |
| Allow offsets to be committed back |  |
| 允许提交偏移量以将偏移量标记为已消费。 |  |
| Configuration options |  |
| 配置参数列表。 |  |
| Parameter |  |
| 一个配置参数。 |  |
| Value |  |
| 参数值。 |  |
| Type |  |
| 值的数据类型。 |  |

### Avro 和 Schema registry

以下是你从 Kafka 服务器消费 Avro Record 值所需的一些选项。
Avro 值的 schema 不会发送到 Kafka，而是发送到 schema registry。因此你需要有一个可用的 schema registry。
以下是你需要设置以在 Confluent Cloud Kafka 实例上使其工作的一些选项。软件栈的各个部分需要身份验证，因此有一些冗余。我们建议你将这些选项放在环境配置文件的变量中。

| Option | Example |
|---|---|
| auto.offset.reset |  |
| earliest (或 latest) |  |
| sasl.jaas.config |  |
| org.apache.kafka.common.security.plain.PlainLoginModule required username="CLUSTER_API_KEY" password="CLUSTER_API_SECRET"; |  |
| security.protocol |  |
| SASL_SSL |  |
| basic.auth.credentials.source |  |
| USER_INFO |  |
| basic.auth.user.info |  |
| CLUSTER_API_KEY:CLUSTER_API_SECRET |  |
| schema.registry.basic.auth.user.info |  |
| SCHEMA_REGISTRY_API_KEY:SCHEMA_REGISTRY_API_SECRET |  |
| sasl.mechanism |  |
| PLAIN |  |
| client.dns.lookup |  |
| use_all_dns_ips |  |
| acks |  |
| ALL |  |
