# 使用 PGP 加密文件

## 描述

`Encrypt files with PGP` action 可使用 GnuPG 包对文件进行加密和签名。

运行环境中必须安装 GnuPG 包，并将加密密钥添加到运行用户的 keystore 中才能正常工作。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| GPG location | GnuPG 可执行文件的路径（例如 `/usr/bin/gpg`）。 |
| UseASCII mode | 加密时是否使用 ASCII 模式。 |
| Include subfolders | 是否包含子文件夹。 |
| Copy previous results to args |  |
| Action | 加密、签名、签名并加密 |
| File/Folder source | 要加密的文件，可通过 Add 添加到 Files/Folders 列表中。 |
| File/Folder destination | 加密文件的目标位置。 |
| Wildcard | 正则表达式通配符。 |
| Files/Folders | 要加密的文件。 |

### 目标文件选项卡

| 选项 | 描述 |
|---|---|
| Create destination folder | 是否创建目标文件夹。 |
| Destination is a file | 目标是否为文件。 |
| Do not keep folder structure | 是否保留文件夹结构。如果不勾选，文件夹结构将被展平。 |
| Add date to filename | 将日期添加到文件名中。 |
| Add time to filename | 将时间添加到文件名中。 |
| Specify Date time format | 是否指定日期时间格式。 |
| Date time format | 日期时间的格式。 |
| Add date before extension | 在扩展名之前还是之后。 |
| If destination file exists a |  |
| Destination folder | 目标文件夹。 |
| Create folder | 是否创建文件夹。 |
| Add date | 添加日期。 |
| Add time | 添加时间。 |
| Specify format | 是否指定日期时间格式。 |
| Add date before extension | 日期时间的格式。 |
| If file exists in destination folder a |  |

### Advanced 选项卡

| 选项 | 描述 |
|---|---|
| Success condition a | 验证成功运行的条件 |
| Nr error lesser than | 错误数少于指定值时成功。 |
| Add files to result files name |  |
