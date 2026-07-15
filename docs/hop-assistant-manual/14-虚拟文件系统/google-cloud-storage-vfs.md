# Google Cloud Storage VFS

## 方案

你可以使用以下方案访问 Google Cloud Storage 中的文件

`**gs://**`

## 配置

你需要为服务账号生成一个密钥文件才能使其工作。
前往 Google Cloud 控制台执行此操作。
一旦你拥有了具有访问 GCP 存储权限的服务账号密钥文件，可以通过名为 `GOOGLE_APPLICATION_CREDENTIALS` 的系统环境变量（Google 的标准做法）或 Options 对话框中的 'Google Cloud' 标签页来指定它。
你也可以使用 `hop-conf`：

```
      -gck, --google-cloud-service-account-key-file=<serviceAccountKeyFile>
                            Configure the path to a Google Cloud service account JSON key file
```
完成后，你将在中心 `hop-config.json` 文件中看到一个 `googleCloud` 条目：

```json
{
  "googleCloud" : {
    "serviceAccountKeyFile" : "/path/to/your/google-key.json"
  }
}
```

## 使用和测试

要测试配置是否有效，请在 GUI 中使用 File/Open 并输入 gs:// 作为文件位置。
然后按回车（或点击刷新按钮）。
浏览到你上传的 CSV、JSON 或文本文件并打开它。
如果一切配置正确，你应该能够在 Hop GUI 中看到内容。
