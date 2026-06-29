# Google Sheets 输出（Google Sheets Output）

Google Sheets 输出转换将数据写入 Google Sheets 工作表。

此转换需要一个 Google 服务账户（JSON 文件）以及一个启用了 Google Drive 和 Google Sheets API 的 Google Cloud 项目。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

### 服务账户（Service account）选项卡

| 选项 | 说明 |
|------|------|
| JSON 凭证密钥文件 | 用于指定或浏览服务账户驱动器中已有的电子表格，或与服务账户电子邮件共享的电子表格。 |
| 应用程序名称（Application Name） | 您在 Google 开发者控制台中为服务账户设置的应用程序名称。 |
| 超时（分钟）（Time out） | 指定 HTTPS 超时时间（分钟，默认为 5）。 |
| 重试次数（Retry attempts） | 针对 Google Sheets API 可重试错误（HTTP 429/500/503）的重试次数。默认为 3。 |
| 重试延迟（秒）（Retry delay） | 可重试错误的初始重试延迟（秒）。默认为 2 秒，后续重试使用指数退避算法。 |
| 模拟（Impersonation） | 允许您模拟您的服务账户。 |

### 代理（Proxy）选项卡

| 选项 | 说明 |
|------|------|
| 代理主机（Proxy host） | 代理服务器主机名。 |
| 代理端口（Proxy port） | 代理服务器端口。 |

### 电子表格（Spreadsheet）选项卡

| 选项 | 说明 |
|------|------|
| 电子表格密钥（Spreadsheet key） | 用于指定或浏览服务账户驱动器中已有的电子表格，或与服务账户电子邮件共享的电子表格。若输入不存在的表名且勾选"创建"复选框，将尝试创建新表。 |
| 工作表 ID（Worksheet Id） | 应从所选电子表格密钥中浏览。如需创建新文件，输入任意将成为所创建电子表格中工作表名称的密钥。 |
| 追加到工作表（Append to sheet） | 将**不含表头**的行追加到现有电子表格。与下方的创建选项不兼容。 |
| 不存在时创建新表（Create new sheet if it does not exist） | 勾选后，若指定的电子表格密钥不存在，将在服务账户驱动器中创建新电子表格（注意该账户没有界面）。 |
