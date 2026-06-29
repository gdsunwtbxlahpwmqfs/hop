# REST 连接（REST Connection）

## 核心功能

REST 连接是一个到 REST API 的连接，可以从诸如"REST 客户端（REST client）"等转换中重复使用。

REST 连接是一个通用目的的元数据项。使用 REST 连接的转换或动作可以在需要时覆盖其属性。

## 主要参数

| 参数 | 说明 |
| --- | --- |
| Name（名称） | 此 REST 连接使用的名称 |
| Base URL（基础 URL） | 用作所有 API 调用基础 URL 的 URL。使用它的客户端转换或动作需要追加此后缀以进行详细的 API 调用。 |
| Test URL（测试 URL） | 可用于测试此 REST 连接的完整 URL。如果未指定测试 URL，则使用基础 URL 执行连接测试。 |
| Authentication type（认证类型） | 选择使用此元数据时 Hop 应如何认证。可选选项见下文。 |

可以在任何文本框中使用环境变量或 Hop 变量（例如 `${PROJECT_HOME}`）。连接在加载元数据时解析变量，因此同一份定义可以从管道、工作流以及 Hop GUI 的测试按钮中重复使用。

## 认证方式

| 类型 | 字段 | 说明 |
| --- | --- | --- |
| No Auth（无认证） | — | 不生成任何认证头。适用于公共端点，或当下游转换手动设置请求头时。 |
| API Key（API 密钥） | 认证头名称、认证前缀（可选）、认证值 | 添加诸如 `Authorization: Bearer <token>` 之类的头，或任何其他自定义头。存在前缀时会与值拼接。 |
| Basic（基本认证） | 用户名、密码 | 向 Jersey 客户端注册 HTTP 基本认证。密码可以加密存储。 |
| Bearer（令牌认证） | Bearer 令牌 | 自动添加 `Authorization: Bearer <token>` 头。 |
| Certificate（证书认证） | 通过下方的 SSL / 客户端证书选项配置 | 通过加载指定的密钥库来启用双向 TLS（mTLS）。 |

## SSL 和客户端证书

| 参数 | 说明 |
| --- | --- |
| Trust store file（信任库文件） | 包含服务器 CA 证书的信任库（JKS 或 PKCS12）路径。留空则使用 JVM 默认信任库。当启用"忽略 SSL 错误"时，Hop 会安装信任所有证书的管理器。 |
| Trust store password（信任库密码） | 信任库文件的密码。支持变量替换和加密值。 |
| Ignore SSL errors（忽略 SSL 错误） | 如果选中，Hop 信任所有证书并跳过主机名校验。仅应在开发或测试环境中使用。 |
| Key store file（密钥库文件） | 用于证书认证的客户端密钥库路径。支持 PKCS12（`.p12`、`.pfx`）和 JKS 文件。 |
| Key store password（密钥库密码） | 密钥库的密码。 |
| Key store type（密钥库类型） | 显式选择 `PKCS12` 或 `JKS`。未指定时默认为 PKCS12。 |

## 相关插件

- REST 客户端（REST client）
