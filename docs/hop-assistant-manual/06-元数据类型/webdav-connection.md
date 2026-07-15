# WebDAV 连接

## 描述

此元数据类型在你的项目中注册一个**命名的** WebDAV 端点。
Hop 将每个连接以**连接名称**作为 VFS 方案公开，因此你可以使用以下方式引用文件：

`myConnectionName:///path/under/webdav/root`

元数据中的 **WebDAV root URL** 必须是完整的 Apache Commons VFS WebDAV URL（HTTP 使用 `webdav4://`，HTTPS 使用 `webdav4s://`），包括到 DAV 根目录的路径（例如 Nextcloud 文件夹根目录，如 `/remote.php/dav/files/username/`）。

凭证在此元数据对象中配置并通过 VFS 应用——它们**不会**嵌入在 Hop 文件路径中。

有关底层 `webdav4` / `webdav4s` URI 语法和选项，请参阅 [VFS](vfs.md)（WebDAV 部分）。

> **❗ 重要:** 许多托管服务器使用 `301` 将 HTTP 重定向到 HTTPS。当服务器需要 TLS 时，请在 **WebDAV root URL** 中使用 **`webdav4s://`**，否则列出或类型检测可能会失败，而桌面客户端（对 WebDAV 方法遵循重定向）可能仍然可以工作。

> **📝 注意:** **Username**、**password** 和 **WebDAV root URL** 支持变量替换。密码可以加密存储在元数据中；Hop 在解析连接时解密它们。

## 选项

| 选项 | 描述 |
|---|---|
| Name | 此连接的名称；用作路径中的 URI 方案（`name:///...`） |
| Description | 可选的较长描述 |
| WebDAV root URL | 包含方案 `webdav4://` 或 `webdav4s://`、主机、可选端口和 DAV 路径的完整 URL（通常带有尾部斜杠）。示例：`webdav4s://cloud.example.com/remote.php/dav/files/admin/` |
| Username | 用于认证的用户名（如果服务器允许匿名访问则可选） |
| Password | 密码或应用密码；支持变量和加密值 |
| Follow HTTP redirects | 传递给 HTTP 客户端（有关 WebDAV 方法重定向限制，请参阅 VFS/WebDAV 提供程序行为） |
| Preemptive basic authentication | 为需要它的服务器主动发送凭证 |

## 提示

- 要验证连接，请打开 **File → Open**（或任何文件对话框）并浏览 `YourConnectionName:///` 或深入子文件夹路径。
- 对于强制 HTTPS 的 Nextcloud 和类似主机，优先使用 **`webdav4s://`**。
- 选择一个不与[内置 VFS 方案](https://commons.apache.org/proper/commons-vfs/filesystems)（`file`、`ftp`、`http` 等）或其他已注册 plugin（`s3`、`azure`、`gs` 等）冲突的 **Name**。
