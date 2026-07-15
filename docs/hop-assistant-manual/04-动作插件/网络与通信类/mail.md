# ![Mail transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mail.svg) Mail

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 地址

此标签页用于定义 Hop 生成的邮件的发件人、联系人和收件人。

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Destination address | 邮件的目标地址。 |
| Cc | 一个邮件地址、以空格分隔的邮件地址列表或通讯组列表，用于发送邮件的抄送副本。 |
| Bcc | 一个邮件地址、以空格分隔的邮件地址列表或通讯组列表，用于发送邮件的密送副本。 |
| Sender name | 您希望邮件显示的发件人姓名。 |
| Sender Address | 您希望邮件显示的发件人邮箱地址。 |
| Reply to | 收件人回复邮件时使用的邮箱地址。 |
| Contact | 关于邮件内容需联系的联系人姓名。 |
| Contact phone | 上一字段中定义的联系人的电话号码。 |

### 服务器

此标签页包含 SMTP 服务器的详细信息，包括身份验证和加密设置。

| Option | Description |
|---|---|
| Mail Connection | 如果在此处选择了邮件服务器连接，则忽略此标签页的其余部分。服务器配置将从 [Mail Server Connection](../../06-元数据类型/mail-server-connection.md) metadata 项中获取。 |
| SMTP server | SMTP 服务器的 URL、主机名或 IP 地址。 |
| Port | SMTP 服务的端口号。 |
| Use authentication | 如果勾选，您可以在接下来的几个字段中输入 SMTP 用户名和密码。 |
| Use XOAUTH2 | 如果勾选，将使用 XOAuth2 向服务器进行身份验证。 |
| Authentication user | 用于服务器身份验证的 SMTP 用户名。 |
| Authentication password | 之前定义的 SMTP 用户名对应的密码。 |
| Use secure authentication | 如果勾选，您可以在下一个字段中指定 SSL 或 TLS 加密。 |
| Secure connection type | 确定服务器使用 SSL 还是 TLS 加密协议。 |
| Check server identity? | 是否检查服务器身份？ |
| Trusted hosts | 以空格分隔的可信主机列表，例如："host1 host2 host3" |

### 邮件消息

此标签页用于设置邮件的文本内容。

| Option | Description |
|---|---|
| Include date in message? | 如果勾选，日期将显示在邮件正文中。 |
| Only send comment in mail body | 如果勾选，邮件中将不包含 pipeline 的相关信息。 |
| Use HTML format in mail body? | 如果勾选，此邮件将使用 HTML 格式而非纯文本格式。 |
| Encoding | HTML 邮件文本的字符编码。 |
| Manage priority | 如果勾选，将启用以下两个字段来设置邮件的优先级和重要性级别。 |
| Priority | 在邮件 metadata 中指定的优先级。 |
| Importance | 在邮件 metadata 中指定的重要性级别。 |
| Sensitivity | 用于设置"Sensitivity"头信息为 Normal、Personal、Private 或 Confidential。 |
| Subject | 邮件的主题行。 |
| Comment | 邮件正文。 |
| Include message (eml) in output | 选择此标志可将生成的 IMF 代码（[Internet Message Format - RFC 5322](https://datatracker.ietf.org/doc/html/rfc5322)）存储到流字段中。然后您可以使用 [Text file output](../../03-转换插件/输出类/textfileoutput.md) transform 通过此字段创建 EML 文件。 |
| Message (eml) output filename | 如果启用了上述标志，请指定包含生成的 IMF 代码的字段名称。 |

### 附件文件

此标签页包含文件附件的相关选项。

| Option | Description |
|---|---|
| Attach content file? | 如果勾选，您将使用接下来的两个字段来定义要使用哪些流字段来创建邮件消息的动态内容。 |
| Content fieldname | 给定字段包含指向用于加载内容的文件的字段。 |
| Filename fieldname | 此字段设置 MimeBodyPart 中的文件名。 |
| Dynamic filenames? | 如果勾选，您将使用接下来的两个字段来定义要使用哪些流字段来为附件创建动态文件名。 |
| Filename field | 您要用于附件动态文件名的流字段。 |
| Wildcard field | 用于为附件创建动态文件名的正则表达式。 |
| Filename/foldername | 要附加的文件的静态名称和位置。 |
| Include subfolders | 如果勾选，将附加指定文件夹的子文件夹中的文件。 |
| Wildcard | 用于标识要附加的文件的正则表达式。 |
| Zip files | 如果勾选，多个文件附件将在附加到邮件之前打包为单个压缩文件。 |
| Is zip filename dynamic? | 如果勾选，zip 压缩文件的名称将由数据流决定。 |
| Zipfilename field | 用于 zip 压缩文件名称的数据字段。 |
| Zip filename | zip 压缩文件的静态名称。 |
| Zip files if size greater than | 仅当文件附件的总大小超过此数值（以字节为单位）时才进行压缩。 |

### 嵌入图片

此标签页包含 HTML 邮件中嵌入图片的相关选项。

| Option | Description |
|---|---|
| Filename | 您要嵌入邮件中的文件的名称和位置。 |
| Content ID | 此文件的唯一标识符。 |
| # | 附件的处理顺序。 |
| Image | 已添加图片的名称。 |
| Content ID (field) | 已添加图片的内容 ID。 |
