# AWS S3（AWS S3 VFS）

## 核心功能

Apache Hop 通过 Apache VFS 支持在平台几乎任何位置读取和写入 AWS S3 bucket。

## 方案（Scheme）

访问 Amazon Simple Storage 中文件所使用的方案为：

`**s3://**`

## 配置

Amazon Web Services Simple Cloud Storage 的配置可以通过多种方式完成。大多数方式需要您拥有 `Access Key`（访问密钥）和 `Secret Key`（秘密密钥）。

**最佳实践是为 Apache Hop 创建一个专用的 IAM 用户**，这样在需要时您可以精细调整权限（例如设置为只读），或者在不再需要时删除该用户。

有关完整列表，请参阅 AWS 文档中的"使用凭据（Working with credentials）"。

以下是两种常用的配置访问方式：

1. 通过环境变量或系统属性配置 AWS 凭据
2. 通过 Hop 的配置选项设置凭据
