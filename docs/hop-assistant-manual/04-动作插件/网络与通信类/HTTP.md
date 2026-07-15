# ![HTTP client transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/http.svg) HTTP client

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform 名称 | transform 的名称；此名称在单个 pipeline 中必须唯一 |
| URL | 基础 URL 字符串 |
| 从字段接受 URL？ | 如果您想从上一个 transform 获取 URL，请启用此选项。 |
| URL 字段名 | 包含 URL 的输入字段名称 |
| 连接超时 |  |
| Socket 超时 | 如果服务器没有返回数据，等待的秒数。 |
| 连接关闭等待时间 |  |
| 结果字段名 | 存储结果的字段名称 |
| HTTP 状态码字段名 | 存储 HTTP 响应码（例如 200、404）的字段名称 |
| 响应时间（毫秒）字段名 | 存储响应时间的字段名称 |
| HTTP 登录 | HTTP (Basic) 身份验证期间传递的用户名 |
| HTTP 密码 | HTTP (Basic) 身份验证期间传递的密码 |
| 代理主机 | 要使用的代理服务器主机名 |
| 代理端口 | 要使用的代理服务器端口号 |
| 参数 | 定义在 URL 上传递的参数名称-值对的区域 |
| 自定义 HTTP Headers | 定义可选 HTTP headers 的区域 |

## 常见问题

### HTTP client transform 不执行任何操作

*问*：HTTP client transform 不执行任何操作，我该如何让它工作？

*答*：HTTP client transform 需要被触发。
使用 Row generator transform 生成例如 1 个空行，并通过跳转将其链接到 HTTP client transform。

### HTTP client transform 和 SOAP

*问*：HTTP client 是否支持 SOAP？

*答*：不支持，它只是调用带有参数的 URL。
未来的 transform 可能会提供 SOAP 功能，支持 WSDL 的 WebService transform 正在开发中。
