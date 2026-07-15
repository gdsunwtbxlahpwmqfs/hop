> **💡 提示:** 此表中的指标可以通过 [Pipeline Log](../06-元数据类型/pipeline-log.md) 在标准 Pipeline 中捕获和处理（例如写入数据库表或 Apache Kafka 主题）

> **⚠️ 警告:** 此表中可用的指标取决于您的 Pipeline 运行配置。
[Local pipeline engine](../07-管道/native-local-pipeline-engine.md) 始终实时显示这些指标。

| 指标 | 描述 |
|---|---|
| Copy | 这些指标适用的 Transform 副本。详见 [Specify Copies](../07-管道/specify-copies.md) |
| Input | 从输入源（如文件、关系型或 NoSQL 数据库等）读取的行数 |
| Read | 来自前一个 Transform 的行数 |
| Written | 离开此 Transform 流向下一个 Transform 的行数 |
| Output | 写入输出目标（如文件、关系型或 NoSQL 数据库等）的行数 |
| Updated | 此 Transform 在输出目标（如文件、关系型或 NoSQL 数据库等）中更新的行数 |
| Rejected | 被 Transform 拒绝并转向错误处理 Transform 的行数 |
| Errors | 此 Transform 执行中未被转向错误处理 Transform 的错误数（在 Transform 上以红色错误三角形标记） |
| Buffers Input | 此 Transform 输入缓冲区中的行数（仅在执行期间可能大于 0） |
| Buffers Output | 此 Transform 输出缓冲区中的行数（仅在执行期间可能大于 0） |
| Duration | 此 Transform 的执行持续时间 |
| Speed | 此 Transform 的执行速度（每秒行数）。该值接近此 Transform 处理（写入或输出）的行数除以持续时间（由于持续时间的舍入而非精确值） |
| Status | Transform 状态；Running、Stopped、Finished、 |
