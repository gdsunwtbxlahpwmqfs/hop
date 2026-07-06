# SFTP上传文件（Put a file with SFTP）

## 功能概述


`Put a file with SFTP` 动作使用安全 FTP 协议将一个或多个文件推送到 SFTP 服务器。
如果指定的文件或模式成功上传，该动作返回 true；如果上传文件时出现问题则返回 false。
> 提示：如果在连接 SFTP 服务器时遇到 `Algorithm Negotiation Fail` 错误，请查看本页末尾的"算法协商失败错误"部分。

## 主要选项

### 常规选项卡

| 选项 | 说明 |
|------|------|
| 工作流动作名称（Workflow action name） | 工作流动作的名称。注意：该名称在单个工作流中必须唯一。一个工作流动作可以多次放置在画布上，但它仍然是同一个工作流动作。 |
