# MinIO

## 方案

你可以使用以下方案访问 [MinIO](https://www.min.io/) 对象存储中的文件

`**minio://**`

## 配置

### Hop GUI

你可以在
[配置视图](hop-gui/perspective-configuration.md) 中的 plugin 标签页下的 "MinIO VFS" 中找到 MinIO VFS 驱动的配置。
你将找到以下配置选项：

| 选项 | 描述 | 变量 |
|---|---|---|
| Access key |  |  |
| 要使用的 MinIO 访问密钥 |  |  |
| `HOP_MINIO_ENDPOINT_HOSTNAME` |  |  |
| Secret key |  |  |
| 要使用的 MinIO 秘密密钥 |  |  |
| `HOP_MINIO_ENDPOINT_PORT` |  |  |
| 端点主机名 |  |  |
| MinIO 服务的主机名 |  |  |
| `HOP_MINIO_ENDPOINT_SECURE` |  |  |
| 端点端口 |  |  |
| MinIO 服务的端口（默认为 9000） |  |  |
| `HOP_MINIO_ACCESS_KEY` |  |  |
| 端点是否安全？ |  |  |
| 如果你想通过 https:// 而非 http 连接，请勾选此选项 |  |  |
| `HOP_MINIO_SECRET_KEY` |  |  |
| 区域 |  |  |
| 要使用的 MinIO 区域 |  |  |
| `HOP_MINIO_REGION` |  |  |
| 对象分片大小 |  |  |
| 你在 MinIO 上的文件的对象分片大小 |  |  |
| `HOP_MINIO_PART_SIZE` |  |  |

### Hop Config

配置视图中可用的相同选项也可以通过 `hop config` 使用：

```bash
-mia, --minio-access-key=<accessKey>
                             The access key to use for Minio VFS
      -mie, --minio-endpoint-secure
                             Secure the Minio service endpoint?
      -mih, --minio-endpoint-host=<endPointHostname>
                             The hostname of the Minio service endpoint
      -mip, --minio-part-size=<partSize>
                             The part size to use for MinIO objects
      -mir, --minio-region=<region>
                             The region to use for the MinIO service
      -mis, --minio-secret-key=<secretKey>
                             The secret key to use for Minio VFS
      -mit, --minio-endpoint-port=<endPointPort>
                             The port of the Minio service endpoint
```

## 使用和测试

要测试配置是否有效，你可以简单地将一个小 CSV 文件上传到 MinIO bucket 中，然后在 Hop GUI 中使用 File/Open。
然后输入 `minio://` 作为文件位置并按回车（或点击刷新按钮）。
浏览到你上传的 CSV 文件并打开它。
如果一切配置正确，你应该能够在 Hop GUI 中看到内容。
