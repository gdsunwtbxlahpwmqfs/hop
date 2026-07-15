# ![Pipeline logging transform Icon, role="image-doc-icon"](../../assets/images/icons/pipeline-log.svg) Pipeline Logging

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported,24](../../assets/images/question_mark.svg) |

## 相关 Plugin

- [Pipeline Log](metadata-types/pipeline-log.md)

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | 此 Transform 的名称 |
| Also log transform details a |  |

## 输出字段

| 字段名 | 类型 | 描述 |
|---|---|---|
| loggingDate | Date | 此 Pipeline 执行的日期和时间 |
| loggingPhase | String | 日志记录时此 Pipeline 所处的阶段（例如 'end'） |
| pipelineName | String | 记录的 Pipeline 名称 |
| pipelineFilename | String | 记录的 Pipeline 文件名 |
| pipelineStart | Date | 记录的 Pipeline 执行开始日期 |
| pipelineEnd | Date | 记录的 Pipeline 执行结束日期 |
| pipelineLogChannelId | String | 记录的 Pipeline 的日志通道 ID |
| parentLogChannelId | String | 记录的 Pipeline 的父日志通道 ID（例如父 Workflow 的通道 ID） |
| pipelineLogging | String | 记录的 Pipeline 的完整日志文本 |
| pipelineErrorCount | Integer | 记录的 Pipeline 执行中发生的错误数量 |
| pipelineStatusDescription | String | 记录的 Pipeline 的状态描述（例如 'Finished'） |
| transformName | String | 记录的 Pipeline 中 Transform 的名称 |
| transformCopyNr | Integer | 当前 Transform 的副本编号 |
| transformStatusDescription | String | 当前 Transform 的状态（例如 'Finished'） |
| transformLogChannelId | String | 当前 Transform 的日志通道 ID |
| transformLoggingText | String | 当前 Transform 的日志文本 |
| transformLinesRead | Integer | 当前 Transform 读取的行数 |
| transformLinesWritten | Integer | 当前 Transform 读取的行数 |
| transformLinesInput | Integer | 当前 Transform 的输入行数 |
| transformLinesOutput | Integer | 当前 Transform 的输出行数 |
| transformLinesUpdated | Integer | 当前 Transform 更新的行数 |
| transformLinesRejected | Integer | 当前 Transform 拒绝的行数 |
| transformErrors | Integer | 当前 Transform 的错误数量 |
| transformStart | Date | 当前 Transform 的开始日期和时间 |
| transformEnd | Date | 当前 Transform 的结束日期和时间 |
| transformDuration | Integer | 当前 Transform 的总持续时间 |
