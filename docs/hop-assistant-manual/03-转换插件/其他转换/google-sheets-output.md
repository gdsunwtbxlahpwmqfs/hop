# ![Google Sheets Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/google-sheets-output.svg) Google Sheets Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### Service account 选项卡

| option | description |
|---|---|
| JSON credential key file | 允许您指定或浏览服务账号驱动器中现有的电子表格，或与服务账号邮箱共享的电子表格。 |
| Application Name | 您在 Google Developer Console 中为服务账号设置的应用名称。 |
| Time out (minutes) | 允许指定 https 超时时间（以分钟为单位，默认为 5）。 |
| Retry attempts | 可重试 Google Sheets API 错误（HTTP 429/500/503）的重试次数。默认为 3。 |
| Retry delay (seconds) | 可重试 Google Sheets API 错误时重试之间的初始延迟秒数。默认为 2 秒，后续重试使用指数退避。 |
| Impersonation | 允许您模拟服务账号。查看 [Google 文档](https://cloud.google.com/iam/docs/impersonating-service-accounts) 了解更多信息。 |

点击 `Test Connection` 按钮验证您为服务账号指定的 JSON 凭据密钥文件是否可以使用 Google Drive 和 Spreadsheets API，并且有权访问您的 Google 电子表格。成功后，您将看到 `Google Drive API: Success!` 消息。

### Proxy 选项卡

| option | description |
|---|---|
| Proxy host | 代理服务器主机名 |
| Proxy port | 代理服务器端口 |

### Spreadsheet 选项卡

| option | description |
|---|---|
| Spreadsheet key | 允许您指定或浏览服务账号驱动器中现有的电子表格，或与服务账号邮箱共享的电子表格。如果输入一个（驱动器中不存在的）工作表名称，当勾选 "create" 复选框时将尝试创建工作表。 |
| Worksheet Id | 应从所选的 spreadsheet key 中浏览。如果要创建新文件，请输入任意 key，该 key 将成为创建的电子表格中 worksheet 的名称 |
| Append to sheet | 将行**不带标题**追加到现有电子表格中。这与下方的 create 选项不兼容。 |
| Create new sheet if it does not exist | 如果勾选此复选框，则当 Spreadsheet key 字段中指定的 Spreadsheet Key 不存在时，将在服务账号驱动器中创建新电子表格（注意此账号没有 UI） |
| Replace sheet if exists | 如果存在则删除并重新创建工作表。此选项可用于避免过多的版本信息，这些信息有时会导致频繁写入 Google Sheet 的 Pipeline 出现问题。 |
| Share Full Rights (RW) with | Share with user email 字段允许您指定将获得新创建文件完全权限的用户邮箱。 |
| Domain Wide Permission | 启用与整个域共享（如果在 Google Drive 中已配置）。 |
