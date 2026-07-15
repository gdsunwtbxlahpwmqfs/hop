# ![Beam Kinesis Produce Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-kinesis-produce.svg) Beam Kinesis Produce

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 限制

Kinesis produce Transform 的主要限制是当前仅支持发送 String 数据。如果你希望支持 Avro 消息和其他格式，请在 GitHub 上创建请求。

## 选项

| Option | Description |
|---|---|
| Transform name |  |
| Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |  |
| AWS access key |  |
| 用于身份验证的 Amazon Web Services 访问密钥。 |  |
| AWS secret key |  |
| 用于身份验证的 Amazon Web Services 密钥。 |  |
| Stream name |  |
| 要写入的 Kinesis 流的名称。 |  |
| The name of the data field to send |  |
| 这是包含实际数据（消息）的字段名称 |  |
| The type of data to send |  |
| 目前仅支持 String 数据（消息）。 |  |
| Partition key |  |
| 要使用的分区键 |  |
| Configuration options |  |
| 配置选项列表。参见 [此处](https://github.com/awslabs/amazon-kinesis-producer/blob/master/java/amazon-kinesis-producer-sample/default_config.properties) 获取示例列表。 |  |
