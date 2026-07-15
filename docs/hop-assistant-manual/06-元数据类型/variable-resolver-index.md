## 功能

你可以定义变量解析器来帮助你解析以下格式的表达式：

```
#{name:key:element}
```
- `name`：用于解析变量的元数据元素名称
- `key`：用于获取值的键或路径。
- `element`：如果获取的值是 JSON 格式，我们可以从中选取某个值（可选）

> **❗ 重要:** 解析变量时的错误不会导致 Pipeline 或 Workflow 层面的失败。

> **📝 注意:** 由于可用性原因，错误不会立即在用户界面中显示。否则将无法输入任何表达式。请查看 [Hop GUI](hop-gui/index.md) 执行窗格中的日志以查看解析器返回的任何错误。

> **💡 提示:** 如果你发现在表视图（表格网格）中输入变量表达式太慢，请在 [configuration perspective](hop-gui/perspective-configuration.md) 中取消选中在工具提示中解析变量的选项。

## 嵌套解析

假设你有一个变量，其值是一个解析表达式。在这种情况下，你会发现当你请求该变量的值时，变量解析器也会被使用。

例如，你可以有一个 `PASSWORD` 变量，在开发环境中指向值 `p@55w0rd`，在生产环境中通过将 `PASSWORD` 设置为解析表达式指向密钥保管库：

```
#{vault:secret/data/database:password}
```
当执行 Pipeline 或 Workflow 时，变量 `PASSWORD` 的值将首先被解析为表达式，然后使用名为 `vault` 的变量解析器从密钥保管库中获取密码。

> **📝 注意:** 目前，变量解析器不进行更多的嵌套解析，以避免无限循环等复杂性。

## Plugin

以下是可用的变量解析器 plugin：

- [Azure Key Vault 变量解析器](metadata-types/variable-resolver/azure-key-vault-variable-resolver.md)
- [Google Secret Manager 变量解析器](metadata-types/variable-resolver/google-secret-manager-variable-resolver.md)
- [Hashicorp Vault 变量解析器](metadata-types/variable-resolver/hashicorp-vault-variable-resolver.md)
- [Pipeline 变量解析器](metadata-types/variable-resolver/pipeline-variable-resolver.md)
