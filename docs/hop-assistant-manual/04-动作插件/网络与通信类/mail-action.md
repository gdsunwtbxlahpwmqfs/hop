# Mail

## 描述

Mail action 发送文本或 HTML 电子邮件，可选择附带文件附件。

此 action 可在 workflow 运行结束时使用，用于通知运行成功或发送 workflow 中失败的通知。

例如，在成功加载后，向分发列表发送电子邮件通知加载成功并附带日志文件是很常见的做法。

如果出现错误，可以发送电子邮件以提醒分发列表上的相关人员。

Mail action 需要 SMTP 服务器。

您可以在连接中使用身份验证和安全设置，但必须拥有 SMTP 凭据。

您可以将文件附加到电子邮件中，例如错误日志和常规日志。

此外，为方便起见，日志可以压缩为单个归档文件。

Mail action 可以使用 action 本身定义的邮件服务器配置，也可以从 [Mail Server Connection](metadata-types/mail-server-connection.md) metadata 类型获取服务器连接。在后一种情况下，action 本身的服务器配置将被忽略。

## 选项

### Addresses 选项卡

| 选项 | 描述 |
|---|---|
| Action 名称 | workflow action 的名称。 |
| 目标地址 | 电子邮件的目标地址；如果用空格分隔，可以指定多个地址。 |
| 抄送 | 邮件的相同副本也会发送到 Cc: 字段中列出的所有地址。 |
| 密送 | 发送给收件人，但其电子邮件地址不会出现在邮件中 |
| 发件人名称 | 发送电子邮件的人员名称 |
| 发件人地址 | 发送电子邮件的人员的电子邮件地址 |
| 回复至 | 发送回复的收件人电子邮件地址 |
| 联系人 | 要放在电子邮件中的联系人姓名 |
| 联系电话 | 要放在电子邮件中的联系电话号码 |

### Server 选项卡

| 选项 | 描述 |
|---|---|
| Mail Connection | 如果在此处选择了邮件服务器连接，则忽略此选项卡的其余部分。服务器配置将从 [Mail Server Connection](metadata-types/mail-server-connection.md) metadata 项中获取。 |
| SMTP 服务器 | SMTP 服务器地址 |
| 端口 | SMTP 服务器运行的端口 |
| 使用身份验证 | 启用以对 SMTP 服务器使用身份验证 |
| 使用 XOAUTH2 | 如果勾选，将使用 XOAuth2 与服务器进行身份验证。 |
| 身份验证用户 | SMTP 用户账户名 |
| 身份验证密码 | SMTP 用户账户密码 |
| 使用安全身份验证？ | 启用以使用安全身份验证 |
| 安全连接类型 | 选择身份验证类型（SSL、TLS、TLS 1.2） |
| 检查服务器身份？ | 检查服务器身份？ |
| 受信任主机 | 以空格分隔的受信任主机列表，例如："host1 host2 host3" |

### Email Message 选项卡

| 选项 | 描述 |
|---|---|
| 在邮件中包含日期？ | 启用以在邮件中包含当前日期 |
| 仅在邮件正文中发送备注？ | 如果不启用，除了备注外，电子邮件还将包含有关 workflow 及其执行的信息 |
| 在邮件正文中使用 HTML | 邮件以 HTML 格式发送 |
| 编码 | 选择 HTML 格式使用的字符编码类型 |
| 管理优先级 a | 启用以设置邮件的优先级和敏感度。通常，失败可能以比成功更高的优先级发送。 |
| 主题 | 在此字段中输入邮件主题。 |
| 备注 | 在此字段中输入邮件正文中的备注。 |

### Attached Files 选项卡

| 选项 | 描述 |
|---|---|
| 在邮件中附加文件？ | 启用以将文件附加到您的电子邮件中 |
| 选择文件类型 a | 要发送的文件在内部文件结果集中定义。 |
| 将文件压缩为单个归档？ | 启用以将附件压缩为 zip 文件 |
| zip 归档名称 | 定义您的 zip 归档文件名 |
| 文件名 | 要添加的单个图像文件名。 |
| Content ID | 自动输入 |
| 图像 | 图像的完整路径（嵌入多个图像时使用）点击 Edit 编辑路径；点击 Delete 删除图像路径 |
| Content ID | 图像内容 ID（嵌入多个图像时使用）点击 Edit 编辑内容 ID；点击 Delete 删除 Content ID |

## 提示

**如何将特定文件附加到电子邮件中**

您可以通过勾选"Attach files to message"选项并在"Select the result files types to attach"下选择要附加的类型，将特定类型的"Result Files"单独或作为单个 ZIP 归档附加到电子邮件中。
什么是 Result File 以及如何创建？
基本上，pipeline 期间创建的任何文件都可能成为 Result File，您只需将其标记为此类。
有几种方法可以做到：

1. 使用支持此功能的 transform 创建文件时，允许它将文件标记为 Result File。
例如，"Text file output" transform 的 File 选项卡上有"Add filenames to result"复选框用于此目的。
文件类型将为 General。
2. 使用 workflow 类别中的"Set files in result" transform 将文件标记为特定类型的结果文件。
此 transform 期望文件名在一个字段中（您可以例如结合使用"Generate Rows"和"Add constants"来生成包含文件名的行）。

**附加 pipeline 的日志**

您可以在发送邮件之前，通过以下方式附加 workflow 先前运行的 pipeline 日志文件。这假设 workflow 由三个 action 组成：Start -> Pipeline -> Mail。

1. 在 Pipeline workflow action 的选项中，勾选"Specify logfile?"并输入文件的名称和扩展名。
您还可以指定所需的日志级别。
2. 在 Mail workflow action 的选项中，勾选"Attach files to message"并（至少）选择文件类型"Log"。
3. 下次发送邮件时，将附带 pipeline 的日志。
