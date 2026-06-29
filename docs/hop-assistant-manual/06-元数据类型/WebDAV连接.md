# WebDAV 连接（WebDAV Connection）

## 核心功能

此元数据类型在您的项目中注册一个**命名的** WebDAV 端点。

Hop 将每个连接以**连接名称**作为 VFS 方案公开，因此您可以通过以下方式引用文件：

`myConnectionName:///path/under/webdav/root`

元数据中的 **WebDAV 根 URL** 必须是完整的 Apache Commons VFS WebDAV URL（HTTP 使用 `webdav4://`，HTTPS 使用 `webdav4s://`），包括到您的 DAV 根的路径（例如 Nextcloud 文件夹根，如 `/remote.php/dav/files/username/`）。

凭据在此元数据对象中配置并通过 VFS 应用——它们**不会**嵌入到 Hop 文件路径中。

**重要提示**：许多托管服务器会使用 `301` 将 HTTP 重定向到 HTTPS。当服务器需要 TLS 时，请在 **WebDAV 根 URL** 中使用 **`webdav4s://`**，否则列表或类型检测可能会失败，而桌面客户端（对 WebDAV 方法会跟随重定向）可能仍然正常工作。

**注意**：**用户名**、**密码**和 **WebDAV 根 URL** 支持变量替换。密码可以在元数据中加密存储；Hop 在解析连接时对其进行解密。

## 主要参数

| 参数 | 说明 |
| --- | --- |
| Name（名称） | 此连接的名称；在路径中用作 URI 方案（`name:///...`） |
| Description（描述） | 可选的较长描述 |
| WebDAV root URL（WebDAV 根 URL） | 包含方案 `webdav4://` 或 `webdav4s://`、主机、可选端口和 DAV 路径的完整 URL（通常带尾部斜杠）。示例：`webdav4s://cloud.example.com/remote.php/dav/files/admin/` |
| Username（用户名） | 用于认证的用户名（如果服务器允许匿名访问则可选） |
| Password（密码） | 密码或应用密码；支持变量和加密值 |
| Follow HTTP redirects（跟随 HTTP 重定向） | 传递给 HTTP 客户端（有关 WebDAV 方法重定向限制，请参阅 VFS/WebDAV 提供程序行为） |
| Preemptive basic authentication（抢占式基本认证） | 为需要它的服务器主动发送凭据 |

## 使用提示

- 要验证连接，请打开**文件 → 打开**（或任何文件对话框），浏览 `YourConnectionName:///` 或进入子文件夹路径。
- 对于强制使用 HTTPS 的 Nextcloud 及类似主机，优先使用 **`webdav4s://`**。
- 选择一个不会与内置 VFS 方案（`file`、`ftp`、`http` 等）或其他已注册插件（`s3`、`azure`、`gs` 等）冲突的**名称**。
