# Azure Storage

> **💡 提示:** 我们仅支持分层命名空间，请确保存储容器已按此配置。

## 方案

你可以使用以下方案访问 Azure Blob Storage 中的文件

`**azure://**`

## 配置

要访问你的 Azure 存储文件，你需要配置以下几项：

- Azure 中存储账户的名称
- Azure 中存储账户的密钥

你可以在 Azure 门户的 Storage Accounts 部分找到这两项。

- 文件块大小：这需要是 512 字节的倍数。

所有 3 个选项都可以在 Hop GUI 选项对话框中设置（菜单：Tools / Options），或使用以下 Hop Conf（`hop-conf.sh` 或 `hop-conf.bat`）命令行选项：

```
      -aza, --azure-account=<account>
                            The account to use for the Azure VFS
      -azi, --azure-block-increment=<blockIncrement>
                            The block increment size for new files on Azure,
                              multiples of 512 only.
      -azk, --azure-key=<key>
                            The key to use for the Azure VFS

```
完成后，你将在中心 `hop-config.json` 文件中看到一个 `azure` 条目：

```json
{
  "azure" : {
    "account" : "storage-account-name",
    "key" : "a/key-comprised-of-a-long-set-of-characters-and-numbers==",
    "blockIncrement" : "1024"
  }
}
```

## 使用和测试

要测试配置是否有效，你可以简单地将一个小 CSV 文件上传到 Azure Storage 文件夹中，然后在 Hop GUI 中使用 File/Open。
然后输入 `azure://` 作为文件位置并按回车（或点击刷新按钮）。
浏览到你上传的 CSV 文件并打开它。
如果一切配置正确，你应该能够在 Hop GUI 中看到内容。
