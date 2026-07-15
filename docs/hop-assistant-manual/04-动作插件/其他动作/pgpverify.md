# 使用 PGP 验证文件签名

## 描述

`Verify file signature with PGP` action 可用于验证文件链接：[签名以确保其真实性](https://www.gnupg.org/gph/en/manual/x135)。

运行环境中必须安装 GnuPG 包，并将加密密钥添加到运行用户的 keystore 中才能正常工作。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| GPG location | GnuPG 可执行文件的路径（例如 `/usr/bin/gpg`）。 |
| Filename | 已签名文件的路径。 |
| Use detached signature | 是否使用分离式签名。 |
| Detached signature | 分离式签名文件的路径（仅当上方的 `Use detached signature` 勾选时可用） |
