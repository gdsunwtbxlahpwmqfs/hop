# ![Workflow logging transform Icon, role="image-doc-icon"](../../assets/images/icons/workflow-log.svg) Workflow logging

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 相关插件

- [Workflow Log](../../06-元数据类型/workflow-log.md)

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | 此 transform 的名称 |
| Also log action details a |  |

## 输出字段

| 字段名 | 类型 | 描述 |
|---|---|---|
| loggingDate | Date | 此 workflow 执行的日期和时间 |
| loggingPhase | String | 记录时此 workflow 所处的阶段（例如 'end'） |
| workflowName | String | 记录的 workflow 名称 |
| workflowFilename | String | 记录的 workflow 文件名 |
| workflowStart | Date | 记录的 workflow 执行开始日期 |
| workflowEnd | Date | 记录的 workflow 执行结束日期 |
| workflowLogChannelId | String | 记录的 workflow 的日志通道 ID |
| parentLogChannelId | String | 记录的 workflow 的父日志通道 ID（例如父 workflow 的通道 ID） |
| workflowLogging | String | 记录的 workflow 的完整日志文本 |
| workflowErrorCount | Integer | 记录的 workflow 执行中发生的错误数 |
| workflowStatusDescription | String | 记录的 workflow 的状态描述（例如 'Finished'） |
| actionName | String | 记录的 pipeline 中 action 的名称 |
| actionNr | Integer | 当前 action 的编号 |
| actionResult | Boolean | 当前 action 的结果（退出代码，Y/N） |
| actionLogChannelId | String | 当前 action 的日志通道 ID |
| actionLoggingText | String | 当前 action 的日志文本 |
| actionErrors | Integer | 当前 action 的错误数 |
| actionLogDate | String | 当前 action 的状态（例如 'Finished'） |
| actionDuration | Integer | 当前 action 的总持续时间 |
| actionExitStatus | Integer | 当前 action 的退出状态 |
| actionNrFilesRetrieved | Integer | 当前 transform 检索到的文件数（如果适用） |
| actionFilename | String | 当前 action 使用的文件名，例如当前 action 运行的 workflow 或 pipeline。 |
| actionComment | String | 添加到日志的可选注释，例如 'Workflow execution finished' |
| actionReason | String | 当前 action 提供的可选原因 |
