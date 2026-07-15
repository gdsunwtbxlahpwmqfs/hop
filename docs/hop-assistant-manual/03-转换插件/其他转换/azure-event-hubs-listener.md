# ![Azure Event Hubs Listener Icon, role="image-doc-icon"](../../assets/images/transforms/icons/azure.svg) Azure Event Hubs Listener

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 连接选项

- Event Hubs namespace：你的 Event Hubs 命名空间名称
- Event Hubs instance name：Event Hub 的名称，即实例本身。
- Event Hub connection string：通常与 SAS Key Connection String 相同
- SAS Policy key name：在 Azure 仪表板的 Event Hubs Namespace 的 "Shared Access Policies" 部分中的策略名称。
该策略需要启用 "Send" 权限。
- SAS Key connection string：你可以使用策略中标记为 "Connection string–primary key" 的值
- Batch size：每次调用 Azure 时以一批发送的消息（事件）数量。
- Message field：包含用作事件的消息的字段。
请注意，你可以使用 JSON Output 或 "Java Script" 等 Transform 来组装消息。
- Consumer Group name：如果你没有创建特定的组，直接使用 $Default
- Storage Container name：你的存储容器名称
- Storage Connection String：来自你的存储账户 Access keys 部分的 key1 或 key2 连接字符串。

## 调优选项

- Batch size：一次从事件中心获取的事件数量，并（可选地）传递给批处理 Pipeline（见下文）
- Prefetch size：（可选）预获取的事件数量

## 输出字段

- Message output field：包含消息（事件）的输出字段名称
- Partition ID field：分区 ID 的输出字段名称
- Offset field name：事件偏移量的输出字段名称
- Sequence number field name：事件序列号的输出字段名称
- Host (owner) field name：事件主机的输出字段名称
- Enqueued time field name：事件入队时间的输出字段名称

## 批处理选项

为了确保在对事件流设置检查点之前所有记录都被安全处理，我们需要在检查点之前运行所有需要运行的逻辑，更新所有需要更新的内容。
为此，我们将一批中读取的所有行传递给指定的批处理 Pipeline（将以单线程运行）

- Batch pipeline：批处理 Pipeline 文件名
- pipeline input transform：批处理 Pipeline 中用于发送事件的 "Injector" 或 "Get records from Stream" Transform 的名称。
- pipeline output transform：（可选）批处理 Pipeline 中要读取的 Transform 名称，用作此 Listener Transform 的输出。
- Maximum wait time (ms)：（可选但强烈建议！）在批处理 Pipeline 执行之前等待的最长时间，即使批处理中可能没有达到指定的记录数。
当你可能没有来自中心的新事件到达时，这有助于防止旧记录长时间卡在批处理 Pipeline 输入中。

## 重要说明

批处理 Pipeline 以 Injector Transform 开始。
它接收以下字段：

- message (String)
- partitionId (String)
- offset (String)
- sequenceNumber (Integer)
- host (String)
- enqueuedTime (Timestamp)
