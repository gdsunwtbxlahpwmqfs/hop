# HTTP Post（HTTP Post）

HTTP Post 转换使用 HTTP POST 命令通过 URL 提交表单数据。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

### 常规（General）选项卡

常规选项卡定义您要使用的 Web 服务 URL，以及（可选）包含 URL 的字段。

| 选项 | 说明 |
|------|------|
| URL | 要提交到的 Web 服务 URL。 |
| 是否从字段接受 URL？（Accept URL from field?） | 勾选后，必须指定从哪个字段获取 URL。 |
| URL 字段名（URL field name） | 若上一选项勾选，在此指定 URL 字段。 |
| 编码（Encoding） | 所访问文件的编码标准。 |
| 请求实体字段（Request entity field） | 包含 POST 请求的字段名称。启用"提交文件"选项时，将检索此字段中命名的文件并提交其内容。 |
| 提交文件（Post a file） | 若在请求实体字段中定义了文件，勾选此选项将提交其内容。 |
| 使用分段上传（Use MultiPart Upload） | 以分段请求的形式将数据发送到服务器，提交文件时很有用。 |
| 连接超时（Connection timeout） | 连接尝试出错前的超时时间（毫秒，默认 10000）。 |
| 套接字超时（Socket timeout） | 套接字出错前的超时时间（毫秒，默认 10000）。 |
| 连接关闭等待时间（Connection close wait time） | 连接关闭后的等待时间（毫秒），默认 -1 表示使用操作系统默认等待时间（通常为 2 分钟）。 |
| 结果字段名（Result fieldname） | 您要将结果输出到的字段。 |
| HTTP 状态码字段名（HTTP status code fieldname） | 您要将状态码输出到的字段。 |
| 响应时间（毫秒）字段名（Response time fieldname） | 您要将响应时间（毫秒）输出到的字段。 |
| HTTP 登录（HTTP login） | 若表单需要身份验证，此字段应包含用户名。 |
| HTTP 密码（HTTP password） | 若表单需要身份验证，此字段应包含与用户名对应的密码。 |
| 代理主机（Proxy host） | 代理服务器的主机名或 IP 地址（若使用）。 |
| 代理端口（Proxy port） | 代理服务器的端口号（若使用）。 |

### 字段选项卡：正文（标头）参数

字段选项卡定义 HTTP 请求标头和正文的参数。正文参数用于 POST 和 PUT 操作。
