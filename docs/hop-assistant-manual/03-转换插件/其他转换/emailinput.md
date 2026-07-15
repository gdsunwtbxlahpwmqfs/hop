# ![Email Messages Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mailinput.svg) Email Messages Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规

在此选项卡上，你将找到常规邮件服务器连接设置：

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Mail Connection | 如果在此选择了邮件服务器连接，则忽略此选项卡的其余部分。服务器配置将从 [Mail Server Connection](../../06-元数据类型/mail-server-connection.md) 元数据项中获取。 |
| Source host | 邮件服务器主机 |
| Use SSL? | 如果你的服务器需要 SSL 连接，请勾选此选项 |
| Use XOAUTH2 | 如果勾选，将使用 XOAuth2 向服务器进行身份验证。 |
| Username | 要连接的邮箱用户名 |
| Password | 要使用的密码 |
| Use proxy? | 如果你想通过代理连接，请勾选此选项。 |
| Proxy username | 连接代理的用户名 |
| Fetch in batches? | 如果你想分批检索大量邮件，请启用此选项 |
| Ignore errors reading fields | 有时服务器不支持检索特定信息。 |
| Protocol | 指定标准协议 POP3、IMAP 或 MBOX 来检索邮件 |
| Test Connection button | 这将尝试使用指定的设置连接到邮件服务器。 |

### 设置

**POP3 设置**

| Option | Description |
|---|---|
| Retrieve | 指定"所有邮件"或"检索前... 封邮件" |
| Retrieve the ... first emails | 允许你指定一次最多要检索多少封邮件 |

**IMAP 设置**

| Option | Description |
|---|---|
| Get folder from field | 启用此选项以允许 IMAP 文件夹名出现在此 Transform 的输入字段中。 |
| Folder field | 如果启用了上述选项，则为文件夹字段。 |
| IMAP folder | 要从中检索邮件的 IMAP 文件夹 |
| Test folder... button | 用于测试文件夹是否有效 |
| Open button | 用于选择 IMAP 文件夹 |
| Include subfolders | 勾选此选项也可从子文件夹检索邮件 |
| Retrieve | 指定要检索哪些邮件：获取全部、新的、旧的、已读、未读、已标记、未标记、草稿、非草稿、已回复或未回复的邮件。 |
| Retrieve the first ... emails | 指定最多检索多少封邮件 |

**批量设置**

| Option | Description |
|---|---|
| Batch size | 一次在一个批次中检索的邮件数量 |
| Start at message number | 开始检索的第一个消息编号 |
| End at message number | 结束检索的最后一个消息编号 |

### 过滤器

这些是你可以在邮件消息头上设置的过滤器。
邮件头匹配你的过滤器的邮件将被检索，其余的被忽略。

| Option | Description |
|---|---|
| Sender (FROM) | 仅检索 |
