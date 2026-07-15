# Ping 主机

## 描述

`Ping a host` action 可使用 ICMP 协议 ping 主机。

当主机可达时返回 true，否则返回 false。

## 选项

| 选项 | 描述 |
|---|---|
| Workflow action name | Workflow action 的名称。 |
| Host name/IP | 要 ping 的主机名或 IP 地址 |
| Ping type a | 发送到指定主机名或 IP 地址的 ping 类型 |
| Timeout (ms) | 使用系统 ping 选项时的超时时间（毫秒）。 |
| Nr. packets to send | 要发送的数据包数量（默认为 2，仅由 Classic ping 选项使用）。 |
