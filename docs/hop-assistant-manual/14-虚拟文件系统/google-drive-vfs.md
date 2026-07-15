# Google Drive VFS

## 方案

你可以使用以下方案访问 Google Drive 中的文件

`**googledrive://**`

## 配置

你需要生成一个凭证文件才能使其工作。
按照 [Google 文档](https://developers.google.com/drive/api/quickstart/java#authorize_credentials_for_a_desktop_application) 查看如何操作。
你还需要指定一个用于保存安全令牌的文件夹。
你可以在 Hop 系统配置选项中指定这两者。
这可以在 Hop GUI 中完成：转到 Options 对话框（从 Tools 菜单）中的 "Google Drive" 标签页。
你也可以使用带有以下选项的 `hop-conf` 脚本：

```
      -gdc, --google-drive-credentials-file=<credentialsFile>
                            Configure the path to a Google Drive credentials JSON
                              file
      -gdt, --google-drive-tokens-folder=<tokensFolder>
                            Configure the path to a Google Drive tokens folder
```
完成后，你将在中心 `hop-config.json` 文件中看到一个 `googleDrive` 条目：

```json
{
  "googleDrive" : {
    "credentialsFile" : "/path/to/google-drive-credentials.json",
    "tokensFolder" : "/path/to/tokens"
  }
}
```

当你第一次尝试运行时，你会在执行 Hop GUI 或 Hop Run 的控制台上看到一条消息，内容类似于：

```
Please open the following address in your browser:
  https://accounts.google.com/o/oauth2/auth?access_type=offline&client_id=yourClientId&redirect_uri=http://localhost:8888/Callback&response_type=code&scope=https://www.googleapis.com/auth/drive
```
在浏览器中打开该 URL 并对给定的客户端 ID 进行认证。
然后你将在配置的 `tokens folder` 中获得一个令牌，用于后续工作。

## 使用和测试

要测试配置是否有效，你可以简单地将一个小 CSV 文件放入 Google Drive，然后在 Hop GUI 中使用 File/Open。
然后输入 googledrive:// 作为文件位置并按回车（或点击刷新按钮）。
浏览到你上传的 CSV 文件并打开它。
如果一切配置正确，你应该能够在 Hop GUI 中看到内容。
