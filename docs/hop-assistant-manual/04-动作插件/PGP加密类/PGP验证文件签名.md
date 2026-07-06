# PGP验证文件签名（Verify file signature with PGP）

## 功能概述


`Verify file signature with PGP` 动作可用于验证文件[签名以确保其真实性](https://www.gnupg.org/gph/en/manual/x135.html)。
GnuPG 包必须安装在运行时环境中，并且加密密钥必须添加到运行时用户密钥库中才能正常工作。

## 主要选项

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| GPG 位置（GPG location） | GnuPG 可执行文件的路径（例如 `/usr/bin/gpg`）。 |
| 文件名（Filename） | 已签名文件的路径。 |
| 使用分离签名（Use detached signature） | 是否使用分离签名。 |
| 分离签名（Detached signature） | 分离签名文件的路径（仅当上方"使用分离签名"勾选时可用）。 |
