# 通过 SFTP 获取文件

## 描述

`Get a file with SFTP` action 使用安全 FTP 协议从 FTP 服务器检索一个或多个文件。

如果指定的文件或模式成功检索，则 action 返回 true；如果检索文件时出现问题，则返回 false。

> **💡 提示:** 如果在连接 SFTP 服务器时遇到 `Algorithm Negotiation Fail` 错误，请查看本页末尾的 [Algorithm Negotiation Fail 错误](#negotiation_error) 部分。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| SFTP-server name / IP | SFTP 服务器名称或 IP 地址 |
| Port | 要使用的 TCP 端口。 |
| User name | 登录 SFTP 服务器的用户名 |
| Password | 登录 SFTP 服务器的密码 |
| Use private keyfile | 是否使用私钥文件。 |
| Private key filename | 如果勾选了 _Use private keyfile_，则此字段可用。 |
| Key passphrase | 如果勾选了 _Use private keyfile_，则此字段可用。 |
| Proxy type a | 用于连接的代理类型。可用选项为： |
| Proxy host | 用于连接的代理主机 |
| Proxy username | 用于连接的代理用户名 |
| Proxy password | 用于连接的代理密码 |
| Compression a | 此 SFTP 连接使用的压缩方式。可用选项为 |

### Files 选项卡

| 选项 | 描述 |
|---|---|
| Copy previous results to args | 如果要检索的文件是由另一个 pipeline 动态生成的，请勾选此选项。 |
| Remote Directory | SFTP 服务器上要从中检索文件的远程目录。 |
| Wildcard (regular expression) a | 如果要选择多个文件，请在此指定正则表达式。如果上方启用了 _Copy previous results to args_ 标志，则此字段被禁用。 |
| Remove files after retrieval | 勾选此标志可在下载后删除远程文件。请注意，此操作通常无法撤消！ |
| Target Directory | Hop 运行的机器上要放置传输文件的目录 |
| Create target folder | 如果目标文件夹不存在且应被创建，请勾选此选项。 |
| Preserve timestamp | 如果勾选，源文件的最后修改时间戳将保留在下载的文件中。如果不勾选，下载的文件的最后修改时间戳将更新为文件下载发生时的时刻。 |
| Add filenames to result | 如果勾选，下载文件的信息将添加到结果文件流中。 |

## Algorithm Negotiation Fail 错误 [[negotiation_error]]

如果您的 `Get a file with SFTP` action 返回如下错误消息，则您（或您连接的服务器）可能使用了在最近 Qi Hop 版本中此 action 所使用的依赖项中被认为不安全的密钥类型。

`com.jcraft.jsch.JSchhAlgoNegoFailException: Algorithm negotiation fail...`

更好、更安全的做法是将密钥升级为更安全的类型。不过，您仍可以通过在启动命令中将以下选项添加到 `HOP_OPTIONS` 变量来连接此 SFTP 服务器（在 `hop-gui.sh/bat` 或 `hop-run.sh/bat` 中，或在您的 `HOP_OPTIONS` 环境变量中）：

`-Djsch.client_pubkey=ssh-rsa,ssh-ed25519,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521,rsa-sha2-512,rsa-sha2-256 -Djsch.server_host_key=ssh-ed25519,ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521,rsa-sha2-512,rsa-sha2-256,ssh-rsa`
