# 通过 SFTP 上传文件

## 描述

`Put a file with SFTP` action 使用安全 FTP 协议将一个或多个文件推送到 SFTP 服务器。

如果指定的文件或模式成功上传，则 action 返回 true；如果上传文件时出现问题，则返回 false。

> **💡 提示:** 如果在连接 SFTP 服务器时遇到 `Algorithm Negotiation Fail` 错误，请查看本页末尾的 [Algorithm Negotiation Fail 错误](#negotiation_error) 部分。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Workflow action name | Workflow action 的名称。 |
| SFTP-server name (IP) | SFTP 服务器名称或 IP 地址。 |
| SFTP port | 要使用的 TCP 端口。 |
| User name | 登录 SFTP 服务器的用户名。 |
| Password | 登录 SFTP 服务器的密码。 |
| Use private keyfile | 是否使用私钥文件。 |
| Private key filename | 如果勾选了 `Use private keyfile`，则此字段可用。 |
| Key passphrase | 如果勾选了 `Use private keyfile`，则此字段可用。 |
| Proxy type | 指定代理服务器的代理类型（SOCKS5、HTTP）。 |
| Proxy host | 代理服务器的主机名或 IP 地址。 |
| Proxy port | 要连接的代理服务器端口。 |
| Proxy username | 登录代理服务器的用户名。 |
| Proxy password | 与 `Proxy username` 关联的密码。 |
| Compression a | 文件传输中使用的压缩方式。 |

### Files 选项卡

| 选项 | 描述 |
|---|---|
| Copy previous results to args | 如果要发送的文件名是由另一个 pipeline 作为结果动态生成的，请勾选此选项。 |
| Copy previous result files to args | 如果要发送的文件是由另一个 pipeline 或 workflow action 动态生成的，请勾选此选项。 |
| Local directory | Hop 运行的机器上要上传文件所在的目录 |
| Wildcard (regular expression) a | 如果要选择多个文件，请在此指定正则表达式。如果启用了 `Copy previous results to args` 或 `Copy previous result files to args` 中的任何一个，则此选项被禁用。 |
| Success when no file | 如果没有文件需要处理时 workflow action 仍需成功，请勾选此选项，否则 workflow 将失败。 |
| After SFTP Put a | 文件传输后采取的操作。可用选项为： |
| Destination folder | 当上方 `After SFTP Put` 选项设置为 `Move file to` 时可用。 |
| Create destination folder | 当 `After SFTP Put` 设置为 `Move file to` 时可用。 |
| Preserve timestamp | 如果勾选，源文件的最后修改时间戳将保留在上传的文件中。如果不勾选，上传的文件的最后修改时间戳将更新为文件上传发生时的时刻。 |
| Add filename to result | 当 `After SFTP Put` 设置为 `Do nothing` 时可用。 |
| Remote directory | SFTP 服务器上要上传文件的远程目录。 |
| Create folder | 如果目标文件夹不存在且应被创建，请勾选此选项。 |

## Algorithm Negotiation Fail 错误 [[negotiation_error]]

如果您的 `Put a file with SFTP` action 返回如下错误消息，则您（或您连接的服务器）可能使用了在最近 Qi Hop 版本中此 action 所使用的依赖项中被认为不安全的密钥类型。

`com.jcraft.jsch.JSchhAlgoNegoFailException: Algorithm negotiation fail...`

更好、更安全的做法是将密钥升级为更安全的类型。不过，您仍可以通过在启动命令中将以下选项添加到 `HOP_OPTIONS` 变量来连接此 SFTP 服务器（在 `hop-gui.sh/bat` 或 `hop-run.sh/bat` 中，或在您的 `HOP_OPTIONS` 环境变量中）：

`-Djsch.client_pubkey=ssh-rsa,ssh-ed25519,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521,rsa-sha2-512,rsa-sha2-256 -Djsch.server_host_key=ssh-ed25519,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521,rsa-sha2-512,rsa-sha2-256,ssh-rsa`
