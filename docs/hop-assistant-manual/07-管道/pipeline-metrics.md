# Pipeline 指标

Hop Gui 在 pipeline 执行期间和执行后显示大量有用的指标。

> **💡 提示:** 此表中的指标可以通过标准 pipeline 捕获和处理（例如写入数据库表或 Apache Kafka 主题），使用 [Pipeline Log](../06-元数据类型/pipeline-log.md)

> **⚠️ 警告:** 此表中可用的指标取决于您的 Pipeline 运行配置。
[本地 pipeline engine](native-local-pipeline-engine.md) 始终实时显示这些指标。

| 指标 | 说明 |
|---|---|
| Copy | 这些指标适用的 transform 副本。详见[指定副本](specify-copies.md) |
| Input | 从输入源（如文件、关系型或 NoSql 数据库等）读取的行数 |
| Read | 来自前一个 transform 的行数 |
| Written | 离开此 transform 向下一个 transform 的行数 |
| Output | 写入输出目标（如文件、关系型或 NoSql 数据库等）的行数 |
| Updated | 在输出目标（如文件、关系型或 NoSql 数据库等）中被 transform 更新的行数 |
| Rejected | 被 transform 拒绝并转移到错误处理 transform 的行数 |
| Errors | 此 transform 执行中未被转移到错误处理 transform 的错误数量（在 transform 上以红色错误三角标记） |
| Buffers Input | 此 transform 输入缓冲区中的行数（仅在执行期间可以大于 0） |
| Buffers Output | 此 transform 输出缓冲区中的行数（仅在执行期间可以大于 0） |
| Duration | 此 transform 的执行持续时间 |
| Speed | 此 transform 的执行速度（每秒行数）。这接近于此 transform 处理（写入或输出）的行数除以持续时间（由于持续时间的舍入不完全精确） |
| Status | Transform 状态：Running、Stopped、Finished、 |
