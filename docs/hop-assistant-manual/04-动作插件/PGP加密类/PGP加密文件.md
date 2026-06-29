# PGP加密文件（Encrypt files with PGP）

`Encrypt files with PGP` 动作可用于使用 GnuPG 包加密和签名文件。

GnuPG 包必须安装在运行时环境中，并且加密密钥必须添加到运行时用户密钥库中才能正常工作。

## 主要选项

### 常规选项卡

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| GPG 位置（GPG location） | GnuPG 可执行文件的路径（例如 `/usr/bin/gpg`）。 |
| 使用 ASCII 模式（Use ASCII mode） | 加密时是否使用 ASCII 模式。 |
| 包含子文件夹（Include subfolders） | 是否包含子文件夹。 |
| 将先前结果复制到参数（Copy previous results to args） | 将先前工作流动作的结果传递给此动作。 |
