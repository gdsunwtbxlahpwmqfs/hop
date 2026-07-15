# 写入日志

## 描述

`Write to log` action 将特定字符串写入 Hop 日志系统。

此 action 类似于 [Write To Log](pipeline/transforms/writetolog.md) transform。

典型用例是将变量值和额外的自定义日志信息写入 Hop 日志系统。

> **💡 提示:** 将（未加密的）密码或其他敏感信息的变量写入 Hop 日志系统时要小心！

参数和变量必须在 Log message 部分中指定。

必须设置日志级别。如果要打印参数和变量，请将 Log detail 级别设置为 Basic。

在 Log message 部分打印参数或变量的示例：``COUNTER: {openvar}COUNTER{closevar}``

> **💡 提示:** 如果错误导致任何内容都无法写入日志，则禁用失败的 Hop，添加日志记录，然后重新运行。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Log level | 要使用的日志级别。有关更多详细信息，请参见[日志记录](logging/logging-basics.md)。 |
| Log subject | 日志行中使用的主题 |
| Log message | 要写入日志的日志消息 |
