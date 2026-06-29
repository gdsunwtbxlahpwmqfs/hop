# 运行SSH命令（Run SSH commands）

运行SSH命令转换允许您通过安全外壳（ssh）TCP/IP 协议执行远程命令。

您可以在命令中将文本传递到 stdout 或 stderr，这些信息随后可以被转换捕获，并通过字段传递给后续转换。

## 主要选项

| 选项 | 说明 |
|------|------|
| 主机名/地址（Hostname/IP address） | SSH 服务器的地址 |
| 端口（Port） | SSH 服务器的端口，默认为 22 |
| 用户名（Username） | SSH 登录用户名 |
| 密码/密钥（Password/Key） | SSH 认证方式，可使用密码或私钥 |
| 命令（Commands） | 要在远程服务器上执行的命令 |
| 输出字段名（Output field name） | 存放命令输出结果的字段名称 |

## 注意事项

- 支持通过 stdout/stderr 捕获命令输出，并通过字段传递给后续转换。
- 支持 Hop Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
