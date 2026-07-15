# ![Beam Kinesis Consume Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-kinesis-consume.svg) Beam Kinesis Consume

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 限制

Kinesis consumer 的主要限制是当前仅支持读取 String 消息。如果你希望支持 Avro 消息和其他格式，请在 GitHub 上创建请求。

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
| 要从中消费的 Kinesis 流的名称。 |  |
| The name of the unique ID output field |  |
| 这是 Transform 输出中将包含接收到的数据（消息）唯一 ID 的字段名称。 |  |
| The name of the data output field |  |
| 这是将包含实际数据（消息）的字段名称。 |  |
| The type of data retrieved |  |
| 目前仅支持 String 数据（消息）。 |  |
| Partition key field name |  |
| 可选：输出中将包含分区键的字段名称 |  |
| Sequence number field name |  |
| 可选：输出中将包含消息序列号的字段名称 |  |
| Sub-sequence number field name |  |
| 可选：输出中将包含消息子序列号的字段名称 |  |
| Shard ID field name |  |
| 可选：输出中将包含消息 Shard ID 的字段名称 |  |
| Stream name field name |  |
| 可选：输出中将包含正在读取的流名称的字段名称 |  |
| Maximum number of records |  |
| 可选：此 Transform 在停止前读取的最大记录数 |  |
| Maximum read time (ms) |  |
| 可选：此 Transform 在停止前读取的最长时间（毫秒） |  |
| Request record limit |  |
| 可选：每次请求（微批处理）中一次性读取的记录数 |  |
| Arrival time watermark policy? |  |
| 在消息到达 Beam Pipeline 时为其设置水位线。 |  |
| Arrival watermark idle time (ms) |  |
| 表示水位线可以空闲的持续时间 |  |
| Processing time watermark? |  |
| 在 Beam Pipeline 中处理消息时为其设置水位线。 |  |
| Fixed delay rate limit policy? |  |
| 指定固定延迟速率限制策略，默认延迟为 1 秒 |  |
| Fixed delay rate limit policy delay (ms) |  |
| 指定具有给定延迟的固定延迟速率限制策略。 |  |
| Maximum capacity per shard |  |
| 指定每个分片的最大消息数。 |  |
