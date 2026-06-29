# MinIO（MinIO VFS）

## 核心功能

使用 MinIO 或其他兼容 S3 的对象存储。

## 方案（Scheme）

访问 MinIO 对象存储中文件所使用的方案为：

`**minio://**`

## 配置

### Hop GUI

您可以在配置视图（configuration perspective）的插件选项卡下的"MinIO VFS"中找到 MinIO VFS 驱动程序的配置。

您将找到以下可配置选项：

| 选项 | 说明 | 变量 |
| --- | --- | --- |

MinIO VFS 配置选项包括服务器端点、端口、访问密钥、秘密密钥、区域等参数，用于连接到 MinIO 或其他 S3 兼容的对象存储服务。
