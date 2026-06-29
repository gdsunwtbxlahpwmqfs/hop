# Azure 认证（Azure Authentication）

## 核心功能

此元数据类型可用于向您的项目中添加新的 Azure 存储文件系统。这允许您同时连接到多个存储账户。

使用 Azure 认证连接到 blob 存储时，需要在路径中指定容器名称。例如，如果您的 Azure 认证命名为 az，则需要将容器指定为 **az:/containerName**。

## 主要参数

| 参数 | 说明 |
| --- | --- |
| Name（名称） | 文件系统名称，将用于文件路径 `name://` |
| Description（描述） | 用于附加信息的描述字段 |
| Storage Account Name（存储账户名称） | Azure 中存储账户的名称 |
| Storage Account Key（存储账户密钥） | 存储账户的主密钥或辅助密钥 |
| Storage Endpoint（存储端点） | 使用默认 Azure 端点时留空，使用兼容服务时填写 |
