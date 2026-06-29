# JavaScript

JavaScript 转换提供了一个用户界面，用于构建可修改数据的 JavaScript 表达式。您在脚本区域中输入的代码会对每个输入行执行一次。

可以将管道中的可用字段作为变量在脚本中使用。

## 主要选项

| 选项 | 说明 |
|------|------|
| 脚本（Script） | JavaScript 脚本代码，对每个输入行执行一次 |
| 兼容模式（Compatibility mode） | 是否启用与旧版本的兼容模式 |
| 字段（Fields） | 脚本中使用的字段及其输出类型 |

## 注意事项

- 提示：在创建字段之前，变量无法使用或测试。点击"获取变量（Get variables）"按钮可将脚本变量转换为字段。
- 支持 Hop Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
