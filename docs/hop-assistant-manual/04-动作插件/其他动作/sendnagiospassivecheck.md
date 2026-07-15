# 发送 Nagios 被动检查

## 描述

`Send Nagios passive check` action 向 Nagios 发送被动检查。

您可以发送监控信息，例如 workflow 中进程的开始和结束。

它需要在 Nagios 服务器上安装 NCSA 附加组件（NSCA 是一个 Linux/Unix 守护进程，允许您将来自远程机器和应用程序的被动警报和检查与 Nagios 集成。

适用于处理安全警报以及冗余和分布式 Nagios 设置。）

有关 Nagios NSCA 附加组件的更多详细信息和安装说明，请参阅 Nagios 链接：[Passive Checks 文档。](http://nagios.sourceforge.net/docs/3_0/passivechecks)

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Host Name/IP Address | Nagios 服务器名称 / IP 地址 |
| Server port | Nagios 服务器端口（通常为 5667）。 |
| Password | 连接到 Nagios 服务器 NSCA 的密码（在 Nagios 服务器和客户端之间共享）。 |
| Connection Timeout | 在指定的连接超时时间（毫秒）后失败（默认 5000）。 |
| Response Timeout | 在指定的响应超时时间（毫秒）后失败（默认 10000）。 |
| Host Name/IP Address | 定义您的发送方主机名 / IP 地址。 |
| Service Name | 定义用于记录事件的服务名称。 |
| Encryption mode a | 连接的加密模式。可用选项为 |
| Level a | 日志级别。可用选项为 |
| Message | 要记录的消息。 |
