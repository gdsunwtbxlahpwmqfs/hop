# Azure 事件中心写入器（Azure Event Hubs Writer）

Azure 事件中心写入器转换允许您将消息（事件）写入 Microsoft Azure 云平台上称为事件中心（Event Hubs）的流式服务总线。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❌ 不支持 |
| Flink | ❌ 不支持 |
| Dataflow | ❌ 不支持 |

## 主要选项

以下是使该转换正常工作需要填写的重要选项：

| 选项 | 说明 |
|------|------|
| Event Hubs 命名空间 | 您的 Event Hubs 命名空间名称。 |
| Event Hubs 实例名称 | Event Hub 的名称，即实例本身。 |
| SAS 策略密钥名称 | Azure 仪表板中 Event Hubs 命名空间"共享访问策略"部分中的策略名称。需要启用"发送"权限的策略。 |
| SAS 密钥连接字符串 | 可使用策略中标记为"连接字符串 – 主密钥"的值。 |
| 批大小（Batch size） | 每次调用 Azure 时以一批发送的消息（事件）数量。 |
| 消息字段（Message field） | 包含用作事件的消息的字段。可使用如 JSON 输出或"JavaScript"等转换来组装消息。 |
