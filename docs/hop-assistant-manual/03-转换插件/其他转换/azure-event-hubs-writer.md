# ![Azure Event Hubs Writer Icon, role="image-doc-icon"](../../assets/images/transforms/icons/azure.svg) Azure Event Hubs Writer

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

以下是使该 Transform 正常工作需要填写的重要选项：

- Event Hubs namespace：你的 Event Hubs 命名空间名称
- Event Hubs instance name：Event Hub 的名称，即实例本身。
- SAS Policy key name：在 Azure 仪表板的 Event Hubs Namespace 的 "Shared Access Policies" 部分中的策略名称。
该策略需要启用 "Send" 权限。
- SAS Key connection string：你可以使用策略中标记为 "Connection string–primary key" 的值
- Batch size：每次调用 Azure 时以一批发送的消息（事件）数量。
- Message field：包含用作事件的消息的字段。
请注意，你可以使用 JSON Output 或 "Java Script" 等 Transform 来组装消息。
