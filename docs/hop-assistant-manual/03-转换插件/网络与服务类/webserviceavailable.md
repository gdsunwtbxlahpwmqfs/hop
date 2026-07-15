# 检查 Web 服务可用性

## 描述

`Check webservice availability` action 检查给定的 URL（例如 Web 服务 URL）是否有效、是否可以连接以及是否可以从中读取数据。

如果在给定的超时时间内连接成功并且可以读取数据，则返回 'true'，否则返回 'false'。

有关失败原因的更多信息可以在日志中作为错误日志 action 找到。

## Options

| Option | Description |
|---|---|
| Action name | Workflow action 的名称。 |
| URL | 指定要验证的 URL。 |
| Connect timeout (ms) | 连接超时时间（毫秒）。 |
| Read timeout (ms) | 连接后，Workflow action 尝试读取数据。 |
