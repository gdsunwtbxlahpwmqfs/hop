# REST 连接

![](../assets/images/icons/rest.svg)

REST 连接是与 REST API 的连接，可以从 [REST client](pipeline/transforms/rest.md) 等 transform 中重复使用。

REST 连接是一个通用元数据项。使用 REST 连接的 transform 或 action 可以在需要时覆盖其属性。

## 相关 plugin

[REST client](pipeline/transforms/rest.md)

## 选项

| 选项 | 描述 |
|---|---|
| Name | 此 REST 连接的名称 |
| Base URL | 用作任何 API 调用基 URL 的 URL。基 URL 需要由使用它的客户端 transform 或 action 追加以进行详细的 API 调用。 |
| Test URL | 可用于测试此 REST 连接的完整 URL。如果未指定测试 URL，将使用基 URL 执行连接测试。 |
| Authentication type | 选择使用此元数据时 Hop 应如何认证。可用选项如下所述。 |

在任何文本框中使用环境变量或 Hop 变量（例如 `{openvar}PROJECT_HOME{closevar}`）。连接在加载元数据时解析变量，因此同一定义可以从 Pipeline、Workflow 和 Hop GUI 测试按钮中重复使用。

## 认证

| 类型 | 字段 | 描述 |
|---|---|---|
| No Auth | — | 不生成认证头。适用于公共端点或下游 transform 手动设置头的情况。 |
| API Key | Authorization header name, Authorization prefix (optional), Authorization value | 添加如 `Authorization: Bearer <token>` 或任何其他自定义头的头。存在前缀时与前缀与值拼接。 |
| Basic | Username, Password | 使用 Jersey 客户端注册 HTTP Basic 认证。密码可以加密存储。 |
| Bearer | Bearer token | 自动添加 `Authorization: Bearer <token>` 头。 |
| Certificate | 通过以下 SSL / Client Certificate 选项配置 | 通过加载指定的密钥库启用双向 TLS（mTLS）。 |

## SSL 和客户端证书

| 选项 | 描述 |
|---|---|
| Trust store file | 包含服务器 CA 证书的信任库（JKS 或 PKCS12）路径。留空使用 JVM 默认信任库。启用 `Ignore SSL errors` 时，Hop 会安装信任所有证书的管理器。 |
| Trust store password | 信任库文件的密码。支持变量替换和加密值。 |
| Ignore SSL errors | 如果选中，Hop 信任所有证书并跳过主机名验证。应仅在开发或测试环境中使用。 |
| Key store file | 用于证书认证的客户端密钥库路径。支持 PKCS12（`.p12`、`.pfx`）和 JKS 文件。 |
| Key store password | 密钥库的密码。 |
| Key store type | 明确选择 `PKCS12` 或 `JKS`。未指定时默认为 PKCS12。 |
| Key password | 可选的密码，用于保护密钥库内的私钥条目。留空时回退到密钥库密码。 |
| Certificate alias | 可选的别名，用于在密钥库中选择特定证书。留空使用默认条目。 |

使用 `Test` 按钮验证所选的认证方法和 SSL 设置是否适用于测试或基 URL。对话框在运行测试之前解析变量，因此与 Pipeline 执行匹配。

## 示例

无
