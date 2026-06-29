# SFTP获取文件（Get a file with SFTP）

`Get a file with SFTP` 动作使用安全 FTP 协议从 FTP 服务器检索一个或多个文件。

如果指定的文件或模式成功检索，该动作返回 true；如果检索文件时出现问题则返回 false。

> 提示：如果在连接 SFTP 服务器时遇到 `Algorithm Negotiation Fail` 错误，请查看本页末尾的"算法协商失败错误"部分。

## 主要选项

### 常规选项卡

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| SFTP 服务器名称/IP（SFTP-server name / IP） | SFTP 服务器名称或 IP 地址。 |
