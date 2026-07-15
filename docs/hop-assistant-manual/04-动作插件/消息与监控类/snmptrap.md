# 发送 SNMP Trap

## 描述

`Send SNMP trap` action 向远程启用 SNMP 的设备发送警报消息。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Server name / IP address | 服务器主机。 |
| Server port | 服务器端口。 |
| OID | 对象标识符。 |
| Test connection | 可用于测试连接。 |
| Target type | 目标类型：Community 或 User。 |
| Community string | 允许访问路由器或其他设备统计信息的用户 ID 或密码。 |
| User | 连接到服务器的用户名 |
| Passphrase | 连接到服务器的密码 |
| Engine ID |  |
| Retry | 重试次数。 |
| Timeout | 超时时间（毫秒） |
| Message | 要发送的消息。 |
