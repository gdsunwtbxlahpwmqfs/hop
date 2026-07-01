# Google Drive（Google Drive VFS）

## 核心功能

hfxt data process 通过 Apache VFS 支持在平台几乎任何位置读取和写入 Google Drive。

## 方案（Scheme）

访问 Google Drive 中文件所使用的方案为：

`**googledrive://**`

## 配置

您需要生成凭据文件才能使其工作。请按照 Google 文档了解如何操作。

您还需要指定一个用于保存安全令牌的文件夹。

您可以在 Hop 系统配置选项中指定这两项。这可以在 Hop GUI 中完成：转到"工具"菜单中的选项对话框的"Google Drive"选项卡。

您也可以使用 `hop-conf` 脚本配合以下选项进行配置：

```shell
# 指定凭据文件路径和安全令牌存储文件夹
```
