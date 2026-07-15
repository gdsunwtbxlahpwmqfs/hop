# Dropbox VFS

## 方案

你可以使用以下方案访问 Dropbox 中的文件

`**dropbox://**`

## 配置

你需要通过使用存储的访问令牌来设置 Dropbox 的 OAuth 2.0 访问。

. 在你的 OAuth2.0 提供者 Dropbox 处创建一个应用程序：（参见 [Dropbox 文档](https://www.dropbox.com/developers/reference/oauth-guide)）
.. 打开浏览器并访问 https://www.dropbox.com/developers
.. 选择 My Apps 并点击 Create app
.. 选择 API dropbox API。选择你需要的访问类型。
为你的应用程序指定一个唯一的名称。
然后，点击 Create App。
Dropbox 将显示你创建的应用程序的 App Settings 面板
.. 在 App Settings 页面中，将 Access token expiration 选择为 'No expiration'，然后点击 Generated Access Token
.. 记下你创建的访问令牌的值

. 在 Hop 系统配置中指定此访问令牌：

** 这可以在 Hop GUI 中完成：转到 Options 对话框（从 Tools 菜单）中的 "Dropbox" 标签页
** 你也可以使用带有以下选项的 `hop-conf` 脚本：

```
      -dbxt, --dropbox-access-token=<accessToken>
                            Configure the access token to Dropbox
```
完成后，你将在中心 `hop-config.json` 文件中看到一个 `dropbox` 条目：

## 使用和测试

要测试配置是否有效，你可以简单地将一个小 CSV 文件放入 Dropbox，然后在 Hop GUI 中使用 File/Open。
然后输入 dropbox:// 作为文件位置并按回车（或点击刷新按钮）。
浏览到你上传的 CSV 文件并打开它。
如果一切配置正确，你应该能够在 Hop GUI 中看到内容。

> **📝 注意:** 目前，此实现无法上传大于 150 MB 的文件。
