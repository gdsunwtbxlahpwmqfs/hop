# Google-Secret-Manager 变量解析器（Google Secret Manager Variable Resolver）

## 核心功能

此变量解析器可以从 Google Secret Manager 检索机密。

## 配置选项

- **Project ID（项目 ID）**：要引用的项目 ID。
- **Location ID（位置 ID）**：位置 ID（可选）。

## 变量表达式格式

使用此插件类型可解析的变量表达式格式如下：

`#{name:secret-id:value-key}`

- name：要使用的变量解析器元数据元素的名称
- secret-id：要检索的机密的 ID。
- value-key：如果值是 JSON 格式，要检索的值的键。

如果未指定 `value-key`，将返回机密的完整字符串。
