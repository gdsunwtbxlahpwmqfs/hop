# MinIO-S3 连接（Minio Connection）

## 核心功能

此元数据类型可用于向您的项目中添加新的 S3 端点。您可以在同一个项目中定义多个 S3 端点。

使用 Minio 客户端连接到 S3 端点时，允许在路径中指定名称。例如，如果您的 Minio 连接命名为 `sss`（Simple Storage Service），则可以将指向某个 bucket 的路径指定为 **sss:///bucketName**。

**注意**：如果您在根目录（在我们的示例中为 `sss://`）创建一个文件夹，将导致创建一个 bucket。

## 主要参数

| 参数 | 说明 |
| --- | --- |
| Name（名称） | 文件系统名称，将用于文件路径：`name://` |
| Description（描述） | 用于附加信息的描述字段 |
| Access key（访问密钥） | 用于连接 S3 服务的访问密钥 |
| Secret key（秘密密钥） | 用于连接 S3 服务的秘密密钥 |
| Endpoint hostname（端点主机名） | 要连接的端点主机名 |
| Endpoint port（端点端口） | 要连接的端点端口（默认 9000） |
| Endpoint secure（端点是否安全） | 如果您希望通过 `https://` 进行通信，请勾选此选项 |
| Region（区域） | 要使用的区域 |
| Object part size（对象分片大小） | 以字节为单位的对象分片大小 |

## 使用提示

- 要测试连接，您可以使用 Hop GUI 的文件对话框（文件 - 打开），输入 `ChosenName://` 来查看 S3 服务的 bucket 列表。
- 为这些 Minio 元数据连接元素取逻辑简单、清晰的名称。
- 取功能性名称而非技术性名称。
- 避免使用连字符等字符。
- 不要使用现有 VFS 驱动程序的名称，无论是内置的（如 file、ftp、http 等）还是通过其他 VFS 插件添加的（如 s3、azure、gs 等）。
