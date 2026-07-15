# Neo4j Perspective

图标：![width="24px"](../assets/images/icons/neo4j_logo.svg)

## 描述

Neo4j perspective 提供了一个视图，允许你从 Neo4j 数据库查询 Hop 执行历史。

该 perspective 显示 `NEO4J_LOGGING_CONNECTION`（用于将执行日志加载到 Neo4j）的值，允许选择要显示日志的 workflow 或 pipeline（可选地指定执行次数并仅显示根执行）。

对于所选的 workflow 或 pipeline（如果未选择则为所有执行），将显示 id、名称、类型、读取、写入、输入、输出和拒绝的行数、错误行数以及执行日期和持续时间。

在 perspective 对话框的下半部分，显示日志文本、错误路径和一些有用的 cypher 查询。

> **💡 提示:** 虽然功能完备，但我们建议你使用 [Neo4j](../06-元数据类型/neo4j-location-type.md) [execution information location](../06-元数据类型/execution-information-location.md) plugin。
