# 虚拟文件系统

## 描述

[Apache 虚拟文件系统 (VFS)](https://commons.apache.org/proper/commons-vfs/) 是 Apache Commons 项目的一部分。
通过 VFS，Hop 用户可以通过 URL 格式从不同来源访问各种文件，例如本地磁盘上的文件、HTTP(S) 服务器上的文件、ZIP 归档内的文件等。

Qi Hop 大量使用 VFS。
除了标准的 VFS 文件系统类型外，我们还添加了 Hop 支持的各种技术栈中的一些类型。
与标准文件系统一样，每种都有其独特的命名方案供你使用。

## Qi Hop VFS 文件系统

下表概述了 Qi Hop 支持的 VFS 文件系统。
点击文件系统名称以访问更详细的文件系统文档。

| 文件系统 | 描述 | URI 格式 |
|---|---|---|
| [AWS S3](vfs/aws-s3-vfs.md) | 提供对 Amazon S3 Bucket 的访问 | `s3://` |
| [Azure Blob Storage](vfs/azure-blob-storage-vfs.md) | 提供对 Azure Blob Storage 的访问 | `azure://` (别名: `azfs://`) |
| [Dropbox](vfs/dropbox-vfs.md) | 提供对 Dropbox 的访问 | `dropbox://` |
| [Google Cloud Storage](vfs/google-cloud-storage-vfs.md) | 提供对 Google Cloud Storage bucket 的访问 | `gs://` |
| [Google Drive](vfs/google-drive-vfs.md) | 提供对 Google Drive 文件夹的访问 | `googledrive://` |
| [Minio connection](metadata-types/minio-connection.md) | 通过命名的 Minio 连接（metadata）提供对 S3 兼容端点的访问 | `<connectionName>://` |
| [WebDAV Connection](metadata-types/webdav-connection.md) | 通过静态方案或命名连接（metadata）提供对 WebDAV 服务器的访问 | `webdav4://`, `webdav4s://`, 或 `<connectionName>://` |

## Apache VFS 文件系统类型

下表列出了默认 Apache VFS 实现提供的文件系统类型。

查看 Apache VFS [文件系统类型](https://commons.apache.org/proper/commons-vfs/filesystems) 以获取有关每个文件系统支持的功能的更多信息。

| 文件系统 | 描述 | URI 格式 |
|---|---|---|
| BZIP2 | 提供对 gzip 和 bzip2 文件内容的只读访问。 |  |
| File | 提供对本地物理文件系统上文件的访问。 |  |
| FTP | 提供对 FTP 服务器上文件的访问。 |  |
| FTPS | 提供通过 SSL 对 FTP 服务器上文件的访问。 |  |
| GZIP | 参见 'bzip2' |  |
| HTTP(S) | 提供对 HTTP 服务器上文件的访问。 |  |
| Jar, Zip and Tar | 提供对 Zip、Jar 和 Tar 文件内容的只读访问。 |  |
| RAM | 将所有数据存储在内存中的文件系统（每个文件内容一个字节数组）。 |  |
| SFTP | 提供对 SFTP 服务器（即 SSH 或 SCP 服务器）上文件的访问。 |  |
| Tar | 参见 'jar' |  |
| Temp | 提供对临时文件系统或暂存区的访问，在 Commons VFS 关闭时被删除。 |  |
| WebDAV | 通过 `commons-vfs2-jackrabbit2` 提供访问。Hop 在 link:[HopVfs.java](https://github.com/apache/hop/blob/main/core/src/main/java/org/apache/hop/core/vfs/HopVfs.java) 中注册提供者。 |  |
| Zip | 参见 'jar' |  |

## 支持的操作

下面的矩阵显示了每个已注册提供者暴露的操作，取自每个提供者在代码中声明的能力集。
能力可能在不同 commons-vfs2 版本之间发生变化，因此权威参考是每个 `FileProvider` / `FileSystem` 类上的 `capabilities` 集合（标准提供者在 `commons-vfs2` 中，Hop 管理的在 `plugins/tech/<plugin>` 中）。
✓ 表示提供者声明了该能力；✗ 表示没有。
服务器端限制（只读挂载、bucket 策略、账户权限等）仍可能在运行时阻止个别操作。

| 方案 | 读取 | 写入 | 追加 | 列表 | 创建/删除 | 重命名 | 随机读 | 随机写 | 最后修改时间 |
|---|---|---|---|---|---|---|---|---|---|
| `s3` | ✓ | ✓ | ✗ | ✓ | ✓ | ✓ | ✓ | ✗ | read |
| `azure` | ✓ | ✓ | ✗ | ✓ | ✓ | ✓ | ✗ | ✗ | read |
| `gs` | ✓ | ✓ | ✗ | ✓ | ✓ | ✗ | ✗ | ✗ | read/set |
| `googledrive` | ✓ | ✓ | ✗ | ✓ | ✓ | ✓ | ✓ | ✗ | read |
| `dropbox` | ✓ | ✓ | ✗ | ✓ | ✓ | ✓ | ✗ | ✗ | read |
| `<minio-conn>` | ✓ | ✓ | ✗ | ✓ | ✓ | ✓ | ✓ | ✗ | read |
| `webdav4`, `webdav4s`, named WebDAV connection | ✓ | ✓ | ✗ | ✓ | ✓ | ✓ | ✓ | ✗ | read |
| `file`, `tmp`, `files-cache` | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | read/set |
| `ram` | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | read/set |
| `zip` | ✓ | ✗ | ✗ | ✓ | ✗ | ✗ | ✗ | ✗ | read |
| `jar` (and `war`/`par`/`ear`/`sar`/`ejb3`) | ✓ | ✗ | ✗ | ✓ | ✗ | ✗ | ✗ | ✗ | read |
| `tar` (and `tbz2`/`tgz`) | ✓ | ✗ | ✗ | ✓ | ✗ | ✗ | ✗ | ✗ | read |
| `gz`, `bz2` | ✓ | ✓ | ✗ | ✓ | ✗ | ✗ | ✗ | ✗ | read |
| `http`, `https` | ✓ | ✗ | ✗ | ✗ | ✗ | ✗ | ✓ | ✗ | read |
| `ftp`, `ftps` | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✗ | read |
| `sftp` | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✗ | read/set |
