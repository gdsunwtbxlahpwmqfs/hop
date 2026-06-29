# Ping主机（Ping a host）

`Ping a host` 动作可用于使用 ICMP 协议 ping 主机。

当主机可达时结果为 true，否则为 false。

## 主要选项

| 选项 | 说明 |
|------|------|
| 工作流动作名称（Workflow action name） | 工作流动作的名称。 |
| 主机名/IP（Host name/IP） | 要 ping 的主机名称或 IP 地址。 |
| Ping 类型（Ping type） | 发送到指定主机名或 IP 地址的 ping 类型。经典 ping：调用操作系统的命令行 ping，当没有数据包丢失时结果为 true。系统 ping：使用 Java 实现的方法。 |
