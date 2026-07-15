# ![Beam Kafka Produce Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-kafka-output.svg) Beam Kafka Produce

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 限制

Kafka Producer 的主要限制是当前仅支持以 String 作为键、以 String 和 Avro Record 作为值进行写入或生产。

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
| Bootstrap servers | 以逗号分隔的主机列表，这些主机是 "bootstrap" Kafka 集群中的 Kafka broker。 |
| The topics | 要发布到的主题。 |
| The field to use as key | 记录键。 |
| The field to use as message | 记录消息。 |

### Avro 和 Schema registry

以下是你向 Kafka 服务器发送 Avro Record 值所需的一些选项。
Avro 值的 schema 不会发送到 Kafka，而是发送到 schema registry。因此你需要有一个可用的 schema registry。
以下是你需要设置以在 Confluent Cloud Kafka 实例上使其工作的一些选项。软件栈的各个部分需要身份验证，因此有一些冗余。我们建议你将这些选项放在环境配置文件的变量中。

| Option | Example |
|---|---|
| schema.registry.url |  |
| https://abcd-12345x.europe-west3.gcp.confluent.cloud |  |
| value.converter.schema.registry.url |  |
| https://abcd-12345x.europe-west3.gcp.confluent.cloud |  |
| auto.register.schemas |  |
| true |  |
| security.protocol |  |
| SASL_SSL |  |
| sasl.jaas.config |  |
| org.apache.kafka.common.security.plain.PlainLoginModule required username="CLUSTER_API_KEY" password="CLUSTER_API_SECRET"; |  |
| username |  |
| CLUSTER_API_KEY |  |
| password |  |
| CLUSTER_API_SECRET |  |
| sasl.mechanism |  |
| PLAIN |  |
| client.dns.lookup |  |
| use_all_dns_ips |  |
| acks |  |
| ALL |  |
| basic.auth.credentials.source |  |
| USER_INFO |  |
| basic.auth.user.info |  |
| CLUSTER_API_KEY:CLUSTER_API_SECRET |  |
| schema.registry.basic.auth.user.info |  |
| SCHEMA_REGISTRY_API_KEY:SCHEMA_REGISTRY_API_SECRET |  |
