# ![Kafka Consumer transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/KafkaConsumerInput.svg) Kafka Consumer

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### General

| Option | Description |
|---|---|
| Transform name | 此 Transform 的名称 |
| Kafka pipeline |  |
| 通过输入路径或点击 _Browse_ 选择路径来指定要执行的 Pipeline。 |  |

### Setup

| Option | Description |
|---|---|
| Bootstrap servers | Kafka 集群中 bootstrap 服务器列表，以逗号分隔 |
| Topics | 输入要从中消费流数据（消息）的每个 Kafka topic 的名称。 |
| Consumer group | 输入您希望此 consumer 所属的组名称。 |

### Batch

使用此选项卡来确定在处理之前要消费多少消息。
您可以指定消息数量和/或特定时间量。

> **📝 注意:** 虽然任一选项都会触发消费，但第一个满足的选项将启动该批次的 Pipeline。

| Option | Description |
|---|---|
| Duration (ms) | 以毫秒为单位指定时间。 |
| Number of records | 指定一个数字。 |
| Offset management a | 选择何时提交 |

### Fields

| Option | Description |
|---|---|
| Input name a | 从 Kafka 流接收的输入名称。 |
| Output name | _Output name_ 可映射到订阅者和成员需求。 |
| Type a | Type 字段定义流式传输记录的数据格式。 |

## 结果字段

从子 Pipeline 中的 Transform 返回字段，以便在此 Pipeline 中进一步处理。

### 选项

使用此选项卡配置 Kafka consumer broker 源的属性格式。
为方便起见，已包含了一些最常见的属性格式。
您可以输入任何所需的 Kafka 属性。
有关这些输入名称的更多信息，请参阅 Apache Kafka 文档站点：https://kafka.apache.org/documentation/。

默认包含的选项有：

| NName | Value |
|---|---|
| auto.offset.reset | latest |
| ssl.key.password |  |
| ssl.keystore.location |  |
| ssl.keystore.password |  |
| ssl.truststore.location |  |
| ssl.truststore.password |  |

### 错误处理
Kafka consumer 支持错误处理，但*仅限于* Batch Size 等于 1 的情况（来自 Kafka 队列的记录逐条处理）。在这种情况下，每当来自 Kafka 队列的记录在被调用的子 Pipeline 中产生错误时，该记录将被发送到错误路径以进行进一步处理。进入错误路径的记录会在 Kafka 中提交，队列中的后续记录可以无问题地处理。除了错误处理插入的错误字段外，流入错误路径的记录布局将是 _Field_ 选项卡中指定的布局。

此功能在 Kafka 队列中某条记录阻止后续记录处理（因为在处理期间产生错误）且我们希望将其从队列中"移除"以便处理队列中剩余记录的情况下非常有用。失败的记录可以保存到某处（例如保存到文件或数据库表中）以供进一步调查。

### Avro 和 Schema registry

以下是从 Kafka 服务器消费 Avro Record 值所需的一些选项。
Avro 值的 schema 不会发送到 Kafka，而是发送到 schema registry。因此，您需要有一个可用的 schema registry。
以下是在 Confluent Cloud Kafka 实例上使其工作所需设置的一些选项。软件栈的各个部分都需要身份验证，因此存在一些冗余。我们建议您将这些选项放在环境配置文件的变量中。

| Option | Example |
|---|---|
| schema.registry.url |  |
| https://abcd-12345x.europe-west3.gcp.confluent.cloud |  |
| key.deserializer |  |
| org.apache.kafka.common.serialization.StringDeserializer |  |
| value.deserializer |  |
| io.confluent.kafka.serializers.KafkaAvroDeserializer |  |
| value.converter.schema.registry.url |  |
| https://abcd-12345x.europe-west3.gcp.confluent.cloud |  |
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
