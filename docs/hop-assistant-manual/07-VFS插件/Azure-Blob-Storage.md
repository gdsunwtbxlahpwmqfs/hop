# Azure Blob Storage（Azure Blob Storage VFS）

## 核心功能

Qi 数据治理平台 通过 Apache VFS 支持在平台几乎任何位置读取和写入 Azure Blob Storage。

**提示**：我们仅支持分层命名空间（hierarchical namespaces），请确保存储容器已按此配置。

## 方案（Scheme）

访问 Azure Blob Storage 中文件所使用的方案为：

`**azure://**`

## 配置

要访问您的 Azure 存储文件，您需要配置以下几项：

- 您在 Azure 中的存储账户名称
- 您在 Azure 中的存储账户密钥

两者都可以在 Azure 门户的"存储账户（Storage Accounts）"部分找到。
