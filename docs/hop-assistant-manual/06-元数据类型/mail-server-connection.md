# 邮件服务器连接

![Mail Server Connection, width=24px](../assets/images/icons/mail.svg)

邮件服务器连接是与邮件服务器的连接，可以从 [Mail](../04-动作插件/网络与通信类/mail.md) 等 action 中重复使用。对其他与电子邮件相关的 action 和 transform 的支持将在后续版本中添加。

## 相关 plugin

[Mail](../04-动作插件/网络与通信类/mail.md) action

## 选项

| 选项 | 描述 |
|---|---|
| Connection name | 此邮件服务器连接的名称 |
| Connection protocol | 此连接使用的连接协议。支持的选项有 SMTP、IMAP 和 POP。目前不支持 MBOX。 |
| Server host | 此连接使用的邮件服务器主机名 |
| Server port | 此连接使用的邮件服务器端口 |
| Use authentication? | 此连接是否使用认证？ |
| User XOAuth2? | 此邮件服务器连接是否使用 XOAuth2 认证？ |
| Username | 此连接使用的用户名 |
| Password | 此连接使用的密码 |
| Use secure authentication? | 此连接是否使用安全认证？ |
| Secure authentication type | 此连接使用的安全认证类型。支持的选项有 SSL、TLS、TLS1.2 |
| Check server identity? | 是否检查服务器身份？ |
| Trusted hosts | 以空格分隔的受信任主机列表，例如："host1 host2 host3" |
| Use proxy? | 此邮件服务器连接是否使用代理服务器？ |
| Proxy username | 此连接使用的代理用户名 |
| Proxy password | 此连接使用的代理密码 |

## 示例

无
