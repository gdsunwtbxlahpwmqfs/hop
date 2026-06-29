# 检查Web服务是否可用（Check if webservice is available）

检查Web服务是否可用转换用于检查数据流中的 Web 服务 URL 是否有效、是否可以连接以及是否可以读取。

如果在给定时间内成功连接并能够读取数据，它将返回布尔值 `true`，否则返回 `false`。启用调试日志（debug logging）时，可以在日志中找到失败原因的更多信息。

## 主要选项

| 选项 | 说明 |
|------|------|
| URL 字段（URL field） | 包含要检查的 Web 服务 URL 的输入字段 |
| 结果字段名（Result fieldname） | 输出布尔结果（true/false）的字段名称 |
| 连接超时（Connect timeout） | 连接超时时间（毫秒） |
| 读取超时（Read timeout） | 读取超时时间（毫秒） |

## 注意事项

- 支持 Hop Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
- 适用于在调用 Web 服务前验证其可用性，避免因服务不可用导致管道失败。
