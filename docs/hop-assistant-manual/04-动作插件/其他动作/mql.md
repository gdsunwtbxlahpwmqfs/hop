# MongoDB QL

## 描述

`MongoDB QL` action 执行来自此 action 配置或文件的 JSON 指令。

此 action 可用于对 Mongo 数据库执行管理指令。

您可以执行多条 JSON 指令。

任何可在 MongoDB 的 runCommand() 中使用的 JSON 指令，都可以由此 action 执行。

MongoDB 文档：https://www.mongodb.com/docs/manual/reference/command/

## 选项

| 选项 | 描述 |
|---|---|
| Action 名称 | workflow action 的名称。 |
| MongoDB Connection | 要使用的 MongoDB 连接。 |
| 使用外部文件 | 启用此选项可从文件加载 JSON 指令。 |
| 文件名 | 包含 JSON 指令的文件的文件名。 |
| 使用变量替换 | 允许在脚本中使用变量。 |
| JSON | 要执行的 JSON 指令。 |

## 示例

单条指令
```json
{"create":"hop_collection"}
```

多条指令
```json
[
    {"create":"hop_collection"},
    {"drop": "hop_collection" }
]
```
