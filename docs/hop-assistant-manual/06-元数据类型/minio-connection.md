# Minio 连接

## 描述

此元数据类型可用于向你的项目添加新的 S3 端点。
你可以在同一项目中定义多个 S3 端点。
使用 Minio 客户端连接到 S3 端点允许你在路径中指定名称。
例如，如果你的 Minio 连接命名为 `sss`（Simple Storage Service），你可以将到 bucket 的路径指定为
**sss:///bucketName**。

**注意**：如果在根目录（在我们的示例中为 `sss://`）创建文件夹，将导致创建一个 bucket。

## 选项

| 选项 | 描述 |
|---|---|
| Name | 文件系统名称，将用于文件路径：`name://` |
| Description | 用于附加信息的描述字段 |
| Access key | 用于连接到 S3 服务的访问密钥 |
| Secret key | 用于连接到 S3 服务的秘密密钥 |
| Endpoint hostname | 要连接的端点主机名 |
| Endpoint port | 要连接的端点端口（默认 9000） |
| Endpoint secure | 如果要通过 `https://` 通信，请勾选此选项 |
| Region | 要使用的区域 |
| Object part size | 对象分片大小（字节） |

## 提示

- 要测试连接，你可以使用 Hop GUI 文件对话框（File - Open）并输入 `ChosenName://` 来查看 S3 服务的 bucket 列表。
- 为这些 Minio 元数据连接元素赋予逻辑性和简单的名称。
- 使用功能性名称而非技术性名称。
- 避免使用连字符等。
- 不要使用已有 VFS 驱动程序的名称，无论是
[内置](https://commons.apache.org/proper/commons-vfs/filesystems)的（如 file、ftp、http 等）还是通过其他 VFS plugin 添加的（如 s3、azure、gs 等）。
