# 检查Web服务可用性（Check Webservice Availability）

`Check webservice availability` 动作检查给定的 URL（例如 Web 服务 URL）是否有效、是否可以连接以及是否可以从中读取数据。

如果在给定的超时时间内连接并且可以读取数据，则返回 'true'，否则返回 'false'。

有关失败原因的更多信息可以在日志中作为错误日志操作找到。

## 主要选项

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| URL | 指定要验证的 URL。 |
| 连接超时（毫秒）（Connect timeout (ms)） | 连接超时时间（毫秒）。该值取决于此 URL 的服务质量和经验。 |
| 读取超时（毫秒）（Read timeout (ms)） | 连接后，工作流动作尝试读取数据。 |
