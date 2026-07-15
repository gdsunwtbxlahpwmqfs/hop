# Azure 认证

## 描述
此元数据类型可用于向你的项目添加新的 Azure Storage 文件系统。这允许你同时连接到多个存储账户。
使用 Azure 认证连接到 blob 存储时，需要在路径中指定容器名称。例如，如果你的 Azure 认证命名为 az，则需要将容器指定为 **az:/containerName**。

## 选项

| 选项 | 描述 |
|---|---|
| Name | 文件系统名称，将用于文件路径 `name://` |
| Description | 用于附加信息的描述字段 |
| Storage Account Name | Azure 中的存储账户名称 |
| Storage Account Key | 存储账户的主密钥或副密钥 |
| Storage Endpoint | 留空使用默认 Azure 端点，或在使用兼容服务时填写 |
