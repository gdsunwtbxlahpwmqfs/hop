# ![Run SSH commands transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/ssh.svg) Run SSH commands

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规 选项卡

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Server name / IP address | 指定要在其上执行命令的机器的服务器名或 IP 地址。 |
| Server port | 服务器上 ssh 协议使用的 TCP/IP 端口。 |
| Timeout | 超时时间（秒）。 |
| Username | 登录使用的用户名。 |
| Password | 使用的密码。 |
| Use key | 如果您想使用私钥登录，请启用此选项 |
| Private key | 私钥文件。 |
| Passphrase | 生成密钥对时使用的可选密码短语。 |
| Proxy host | 使用的代理服务器主机（名称或 IP 地址）。 |
| Proxy port | 使用的代理服务器端口。 |
| Proxy username | 代理用户名。 |
| Proxy password | 代理密码。 |
| Test connection | 使用此按钮检查提供的凭据是否足以登录 SSH 服务器。 |

### 设置 选项卡

**输出**

| 选项 | 描述 |
|---|---|
| Response field name | 包含已执行命令输出的 String 输出字段名称。这是 stdout 和 stderr 命令输出的拼接。 |
| Error response field name | 布尔输出字段的名称，如果发生错误则包含 true，如果 ssh 命令成功执行则包含 false。 |

**命令**

| 选项 | 描述 |
|---|---|
| Get commands from field | 如果要执行在输入字段中指定的命令，请启用此选项 |
| Commands field name | 选择将包含要执行命令的输入字段 |
| Commands | 字段允许您指定要执行的命令。 |
