## 功能

此变量解析器可以从 [Hashicorp Vault](https://www.vaultproject.io/) 获取密钥。
以下是可用的选项：

- Vault address：Vault 服务器的基础地址和端口（例如：https://vault-server:8200）
- Vault token：用于认证的令牌
- Path prefix：可选的路径前缀，添加在解析器表达式中的键路径之前。例如，如果你在此处输入 `kv-other/data`，表达式

```
#{vault:db:password}
```

将在内部解析为：

```
#{vault:kv-other/data/db:password}
```
- Validate HTTPS connections?：建议在生产环境中启用连接验证。这通过以下任一选项中指定的 X.509 证书来保护连接安全。
- PEM file path：包含 X.509 证书字符串的文件（VFS）名称
- PEM string：如果不使用文件，则直接提供 X.509 字符串本身
- Open connection timeout：获取 http(s) 连接时的连接超时时间，以毫秒为单位。
- Read connection timeout：读取时的超时时间，以毫秒为单位。

你可以用此 plugin 类型解析的变量表达式（与以往一样）采用以下格式：

`{openvar}name:path-key:value-key{closevar}`

- name：要使用的变量解析器元数据元素的名称
- path-key：Vault 中密钥的路径。
- value-key：要获取的值的键。

如果不指定 `value-key`，将返回密钥的完整 JSON 字符串。

## 示例

假设我们在 Vault 的 KV 密钥引擎中定义了一个密钥：

image:metadata-types/variable-resolver/vault-variable-resolver-server.png

我们可以定义一个名为 `vault` 的连接，并使用表达式获取值：

- `{openvar}vault:hop/data/some-db:hostname{closevar}` : localhost
- `{openvar}vault:hop/data/some-db:username{closevar}` : john
- `{openvar}vault:hop/data/some-db{closevar}` : `{"db":"test","hostname":"localhost","password":"some-password","port":"3306","username":"john"}`
