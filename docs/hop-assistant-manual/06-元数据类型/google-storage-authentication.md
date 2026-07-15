# Google Storage 认证

## 描述
此元数据类型可用于向你的项目添加新的 Google Cloud Storage 文件系统。这允许你同时连接到多个存储账户。

> **📝 注意:** 我们支持变量，但 VFS 文件系统仅在启动时加载。这意味着仅支持来自 Hop 系统、项目或环境级别的变量。

## 选项

| 选项 | 描述 |
|---|---|
| Name | 文件系统名称，将用于文件路径 `name://` |
| Description | 用于附加信息的描述字段 |
| Storage Account Key | 存储账户的主密钥或副密钥 |
