# ![Google Sheets Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/google-sheets-input.svg) Google Sheets Input

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
| Timeout | 允许指定 https 超时时间（以分钟为单位，默认为 5）。 |
| Impersonation | 允许您模拟服务账号。查看 [Google 文档](https://cloud.google.com/iam/docs/impersonating-service-accounts) 了解更多信息。 |

点击 `Test Connection` 按钮验证您为服务账号指定的 JSON 凭据密钥文件是否可以使用 Google Drive 和 Spreadsheets API，并且有权访问您的 Google 电子表格。成功后，您将看到 `Google Drive API: Success!` 消息。

### Spreadsheet 选项卡

| option | description |
|---|---|
| Spreadsheet Key | 指定要读取的 Google 电子表格的 key。点击 "Browse" 按钮获取可用电子表格列表。 |
| Worksheet Id | 指定要读取的 Google 电子表格中的 worksheet id（标题）。点击 "Browse" 按钮获取所选 Google 电子表格中可用 worksheet 的列表。 |

### Proxy 选项卡

| option | description |
|---|---|
| Proxy host | 代理服务器主机名 |
| Proxy port | 代理服务器端口 |

###  Fields 选项卡

允许您从工作表的字段中进行选择。

字段名称始终在 Google 电子表格的第一行定义。

**Get Fields** 允许您获取字段并猜测其类型、格式、精度、小数和组分隔符以及裁剪类型。
