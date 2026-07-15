# ![HTTP Post transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/httppost.svg) HTTP Post

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规选项卡

常规选项卡定义您要使用的 RSS/Atom URL，以及可选地哪些字段包含 URL。

| 选项 | 描述 |
|---|---|
| URL | 要提交到的 Web 服务 URL。 |
| 从字段接受 URL？ | 如果勾选，您必须指定从哪个字段检索 URL。 |
| URL 字段名 | 如果勾选了上一个选项，在此指定 URL 字段。 |
| 编码 | 要访问文件的编码标准。 |
| 请求实体字段 | 将包含 POST 请求的字段名称。 |
| 发布文件 | 如果在 Request entity 字段中定义了文件，勾选此选项将发布其内容。 |
| 使用 MultiPart 上传 | 这将以 multipart 请求的形式将数据发送到服务器，这在发布文件时很有用 |
| 连接超时 | 定义连接尝试出错时的超时时间（默认 10000）毫秒。 |
| Socket 超时 | 定义 socket 出错时的超时时间（默认 10000）毫秒。 |
| 连接关闭等待时间 | 定义连接关闭后的等待时间（毫秒），默认 -1 表示操作系统的默认等待时间（通常为 2 分钟）。 |
| 结果字段名 | 您要将结果输出发布到的字段。 |
| HTTP 状态码字段名 | 您要将状态码输出发布到的字段。 |
| 响应时间（毫秒）字段名 | 您要将响应时间（毫秒）发布到的字段。 |
| HTTP 登录 | 如果此表单需要身份验证，此字段应包含用户名。 |
| HTTP 密码 | 如果此表单需要身份验证，此字段应包含与用户名对应的密码。 |
| 代理主机 | 代理服务器的主机名或 IP 地址（如果使用）。 |
| 代理端口 | 代理服务器的端口号（如果使用）。 |

### Fields 选项卡：Body (Header) 参数

Fields 选项卡定义 HTTP 请求 header 和 body 的参数。
如果您已在常规选项卡中填写了 URL 和其他必要信息，可以使用 Get values 按钮在此处预填充字段。
Body 参数用于 POST 和 PUT 操作。

| 选项 | 描述 |
|---|---|
| # | 此参数传递给 Web 应用程序的顺序。 |
| 名称 | 包含要映射到参数的值的字段名称。 |
| 参数 | 要将 Name 的值映射到的参数。 |
| 放入 Header？ | 如果设置为 Y，参数将被放入请求 header 中。 |

### Fields 选项卡：Query 参数

Fields 选项卡定义 HTTP 请求 header 和 body 的参数。
如果您已在常规选项卡中填写了 URL 和其他必要信息，可以使用 Get values 按钮在此处预填充字段。
Query 参数在 URL 中指定，可用于任何 HTTP 方法。

| 选项 | 描述 |
|---|---|
| # | 此参数传递给 Web 应用程序的顺序。 |
| 名称 | 包含要映射到参数的值的字段名称。 |
| 值 | 要映射到参数的值。 |
