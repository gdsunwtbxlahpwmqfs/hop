## 功能

此变量解析器可以从 Google Secret Manager 获取密钥。

以下是可用的选项：

- Project ID：要引用的项目 ID。
- Location ID：位置 ID（可选）

你可以用此 plugin 类型解析的变量表达式（与以往一样）采用以下格式：

`{openvar}name:secret-id:value-key{closevar}`

- name：要使用的变量解析器元数据元素的名称
- path-key：要获取的密钥的 ID。
- value-key：当值为 JSON 时，要获取的值的键。

如果不指定 `value-key`，将返回密钥的完整字符串。

## 示例

假设我们在 Secret Manager 中定义了一个 JSON 格式的密钥：

image:metadata-types/variable-resolver/gcp-secret-manager-server.png

我们可以定义一个名为 `google-secret` 的连接，并使用表达式获取值：

- `{openvar}google-secret:json-secret:hostname{closevar}` : localhost
- `{openvar}google-secret:json-secret:username{closevar}` : john
- `{openvar}google-secret:json-secret{closevar}` : `{"db":"test","hostname":"localhost","password":"some-password","port":"3306","username":"john"}`
