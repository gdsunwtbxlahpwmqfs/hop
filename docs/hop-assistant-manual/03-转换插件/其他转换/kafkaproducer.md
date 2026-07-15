# ![Kafka Producer transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/KafkaProducerOutput.svg) Kafka Producer

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name |  |
| 此 Transform 的名称 |  |
| Bootstrap servers |  |
| Kafka 集群中 bootstrap 服务器列表，以逗号分隔 |  |
| Client ID |  |
| 唯一的客户端标识符，用于标识和建立与服务器的持久连接路径以发出请求，并区分不同的客户端。 |  |
| Topic |  |
| 记录发布到的类别。 |  |
| Key Field |  |
| 在 Kafka 中，所有消息都可以设置 key，允许消息根据其 key 按默认路由方案分发到分区。 |  |
| Message Field |  |
| topic 中包含的单个记录。 |  |

### 选项

使用此选项卡配置 Kafka consumer broker 源的属性格式。
为方便起见，已包含了一些最常见的属性格式。
您可以输入任何所需的 Kafka 属性。
有关这些输入名称的更多信息，请参阅 Apache Kafka 文档站点：https://kafka.apache.org/documentation/。

默认包含的选项有：

| Option | Value |
|---|---|
| auto.offset.reset | latest |
| ssl.key.password |  |
| ssl.keystore.location |  |
| ssl.keystore.password |  |
| ssl.truststore.location |  |
| ssl.truststore.password |  |

### Avro 和 Schema registry

以下是向 Kafka 服务器发送 Avro Record 值所需的一些选项。
Avro 值的 schema 不会发送到 Kafka，而是发送到 schema registry。因此，您需要有一个可用的 schema registry。
以下是在 Confluent Cloud Kafka 实例上使其工作所需设置的一些选项。软件栈的各个部分都需要身份验证，因此存在一些冗余。我们建议您将这些选项放在环境配置文件的变量中。

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
