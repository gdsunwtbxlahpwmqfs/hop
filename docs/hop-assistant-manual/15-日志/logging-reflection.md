# 反射

Hop 反射 plugin 为你提供了简单的方法来获取有关 pipeline 或 workflow 内部正在发生的事情的更多信息。
它提供了 3 种主要的执行期间反射方式：

- 记录 pipeline 和 Transform 执行信息
- 记录 workflow 和 Action 执行信息
- 监听特定 Transform 的输出

## Pipeline Log

你可以通过创建一个新的 `Pipeline Log` metadata 对象来让 pipeline 及其 Transform 记录其执行内容。
对于你创建的每个此类型的 metadata 对象，Hop 将执行你选择的 pipeline。
它将所有 pipeline 的运行时信息传递给它。
以下是这些选项：

### 选项

| 选项 | 描述 |
|---|---|
| 名称 |  |
| metadata 对象的名称 |  |
| 启用 |  |
| 使用此项来启用或禁用此对象的 pipeline 日志记录 |  |
| 仅记录父 pipeline？ |  |
| 如果启用此项，则仅记录由 Hop Run、GUI、Server 或 API 执行的父 pipeline。 |  |
| 执行用于捕获日志的 pipeline |  |
| 每次执行 pipeline 时都会执行的 pipeline 的文件名。 |  |
| 在 pipeline 开始时执行？ |  |
| 在 pipeline 开始之前执行。 |  |
| 在 pipeline 结束时执行？ |  |
| 在 pipeline 完成执行后执行。 |  |
| 执行期间定期执行？ |  |
| 如果你想了解长时间运行的 pipeline 的进展，请启用此标志。 |  |
| 间隔（秒） |  |
| 日志 pipeline 定期执行的间隔 |  |

### Transform

`Pipeline Logging` Transform 目前只有一个选项：

- 同时记录 Transform 详情：除了正在运行的 pipeline 的信息外，它还会为每个正在运行或已完成的 Transform 副本提供下面详细说明的一系列字段。

| 字段 | 类型 | 级别 | 描述 |
|---|---|---|---|
| loggingDate |  |  |  |
| 日期 |  |  |  |
| Pipeline |  |  |  |
| 日志记录日期 |  |  |  |
| loggingPhase |  |  |  |
| 字符串 |  |  |  |
| Pipeline |  |  |  |
| 日志记录阶段（见上文） |  |  |  |
| pipelineName |  |  |  |
| 字符串 |  |  |  |
| Pipeline |  |  |  |
| pipeline 名称 |  |  |  |
| pipelineFilename |  |  |  |
| 字符串 |  |  |  |
| Pipeline |  |  |  |
| pipeline 的文件名 |  |  |  |
| pipelineStart |  |  |  |
| 日期 |  |  |  |
| Pipeline |  |  |  |
| pipeline 的开始日期 |  |  |  |
| pipelineEnd |  |  |  |
| 日期 |  |  |  |
| Pipeline |  |  |  |
| pipeline 的结束日期 |  |  |  |
| pipelineLogChannelId |  |  |  |
| 字符串 |  |  |  |
| Pipeline |  |  |  |
| Pipeline 日志通道 ID |  |  |  |
| parentLogChannelId |  |  |  |
| 字符串 |  |  |  |
| Pipeline |  |  |  |
| 父日志通道 ID |  |  |  |
| pipelineLogging |  |  |  |
| 字符串 |  |  |  |
| Pipeline |  |  |  |
| pipeline 的日志文本 |  |  |  |
| pipelineErrorCount |  |  |  |
| 整数 |  |  |  |
| Pipeline |  |  |  |
| 错误数 |  |  |  |
| pipelineStatusDescription |  |  |  |
| 字符串 |  |  |  |
| Pipeline |  |  |  |
| pipeline 状态描述 |  |  |  |
| transformName |  |  |  |
| 字符串 |  |  |  |
| Transform |  |  |  |
| Transform 的名称 |  |  |  |
| transformCopyNr |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| Transform 的副本编号 |  |  |  |
| transformStatusDescription |  |  |  |
| 字符串 |  |  |  |
| Transform |  |  |  |
| Transform 状态描述 |  |  |  |
| transformLogChannelId |  |  |  |
| 字符串 |  |  |  |
| Transform |  |  |  |
| Transform 日志通道 ID |  |  |  |
| transformLoggingText |  |  |  |
| 字符串 |  |  |  |
| Transform |  |  |  |
| Transform 日志文本 |  |  |  |
| transformLinesRead |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 读取行数 |  |  |  |
| transformLinesWritten |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 写入行数 |  |  |  |
| transformLinesInput |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 输入行数 |  |  |  |
| transformLinesOutput |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 输出行数 |  |  |  |
| transformLinesUpdated |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 更新行数 |  |  |  |
| transformLinesRejected |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 被错误处理拒绝的行数 |  |  |  |
| transformErrors |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 错误数 |  |  |  |
| transformStart |  |  |  |
| 日期 |  |  |  |
| Transform |  |  |  |
| 执行开始 |  |  |  |
| transformEnd |  |  |  |
| 日期 |  |  |  |
| Transform |  |  |  |
| 执行结束 |  |  |  |
| transformDuration |  |  |  |
| 整数 |  |  |  |
| Transform |  |  |  |
| 执行持续时间（毫秒） |  |  |  |

## Workflow Log

你可以通过创建一个新的 `Workflow Log` metadata 对象来让 workflow 及其 Action 记录其执行内容。
对于你创建的每个此类型的 metadata 对象，Hop 将执行你选择的 pipeline。
它将所有 workflow 的运行时信息传递给它。
以下是这些选项：

### 选项

| 选项 | 描述 |
|---|---|
| 名称 |  |
| metadata 对象的名称 |  |
| 启用 |  |
| 使用此项来启用或禁用此对象的 workflow 日志记录 |  |
| 仅记录父 workflow？ |  |
| 如果启用此项，则仅记录由 Hop Run、GUI、Server 或 API 执行的父 workflow。 |  |
| 执行用于捕获日志的 pipeline |  |
| 每次执行 workflow 时都会执行的 pipeline 的文件名。 |  |
| 在 workflow 开始时执行？ |  |
| 在 workflow 开始之前执行。 |  |
| 在 workflow 结束时执行？ |  |
| 在 workflow 完成执行后执行。 |  |
| 执行期间定期执行？ |  |
| 如果你想了解长时间运行的 workflow 的进展，请启用此标志。 |  |
| 间隔（秒） |  |
| 日志 pipeline 定期执行的间隔 |  |

### Transform

`Workflow Logging` Transform 目前只有一个选项：

- 同时记录 Action 详情：除了正在运行的 workflow 的信息外，它还会为每个正在运行或已完成的 Action 提供下面详细说明的一系列字段。

| 字段 | 类型 | 级别 | 描述 |
|---|---|---|---|
| loggingDate |  |  |  |
| 日期 |  |  |  |
| Workflow |  |  |  |
| 日志记录日期 |  |  |  |
| loggingPhase |  |  |  |
| 字符串 |  |  |  |
| Workflow |  |  |  |
| 日志记录阶段（见上文） |  |  |  |
| workflowName |  |  |  |
| 字符串 |  |  |  |
| Workflow |  |  |  |
| Workflow 名称 |  |  |  |
| workflowFilename |  |  |  |
| 字符串 |  |  |  |
| Workflow |  |  |  |
| Workflow 文件名 |  |  |  |
| workflowStart |  |  |  |
| 日期 |  |  |  |
| Workflow |  |  |  |
| 执行开始 |  |  |  |
| workflowEnd |  |  |  |
| 日期 |  |  |  |
| Workflow |  |  |  |
| 执行结束 |  |  |  |
| workflowLogChannelId |  |  |  |
| 字符串 |  |  |  |
| Workflow |  |  |  |
| Workflow 日志通道 ID |  |  |  |
| workflowParentLogChannelId |  |  |  |
| 字符串 |  |  |  |
| Workflow |  |  |  |
| 父日志通道 ID |  |  |  |
| workflowLogging |  |  |  |
| 字符串 |  |  |  |
| Workflow |  |  |  |
| workflow 的日志文本 |  |  |  |
| workflowErrorCount |  |  |  |
| 整数 |  |  |  |
| Workflow |  |  |  |
| 错误数 |  |  |  |
| workflowStatusDescription |  |  |  |
| 字符串 |  |  |  |
| Workflow |  |  |  |
| Workflow 状态描述 |  |  |  |
| actionName |  |  |  |
| 字符串 |  |  |  |
| Action |  |  |  |
| Action 名称 |  |  |  |
| actionNr |  |  |  |
| 整数 |  |  |  |
| Action |  |  |  |
| Action 编号 |  |  |  |
| actionResult |  |  |  |
| 布尔值 |  |  |  |
| Action |  |  |  |
| 结果（true/false） |  |  |  |
| actionLogChannelId |  |  |  |
| 字符串 |  |  |  |
| Action |  |  |  |
| Action 的日志通道 ID |  |  |  |
| actionLoggingText |  |  |  |
| 字符串 |  |  |  |
| Action |  |  |  |
| Action 的日志文本 |  |  |  |
| actionErrors |  |  |  |
| 整数 |  |  |  |
| Action |  |  |  |
| 错误数 |  |  |  |
| actionLogDate |  |  |  |
| 日期 |  |  |  |
| Action |  |  |  |
| Action 日志记录日期 |  |  |  |
| actionDuration |  |  |  |
| 整数 |  |  |  |
| Action |  |  |  |
| Action 持续时间 |  |  |  |
| actionExitStatus |  |  |  |
| 整数 |  |  |  |
| Action |  |  |  |
| Action 退出状态（shell 脚本的） |  |  |  |
| actionNrFilesRetrieved |  |  |  |
| 整数 |  |  |  |
| Action |  |  |  |
| 检索到的文件数（从远程系统） |  |  |  |
| actionFilename |  |  |  |
| 字符串 |  |  |  |
| Action |  |  |  |
| Action 的文件名（如果有引用） |  |  |  |
| actionComment |  |  |  |
| 字符串 |  |  |  |
| Action |  |  |  |
| Action 注释 |  |  |  |
| actionReason |  |  |  |
| 字符串 |  |  |  |
| Action |  |  |  |
| Action 原因 |  |  |  |

## Pipeline Probe

你可以将一个或多个 pipeline Transform 的输出以标准化方式流式传输到另一个 pipeline。
你可以通过创建一个 `Pipeline Probe` metadata 对象来实现此目的。
以下是它的选项：

### 选项

| 选项 | 描述 |
|---|---|
| 名称 |  |
| pipeline 探针的名称 |  |
| 启用 |  |
| 启用后，所有指定 pipeline Transform 的数据将流式传输到指定的 pipeline |  |
| 执行用于捕获数据的 pipeline |  |
| 每次执行指定的源 pipeline（包含指定的源 Transform）时将执行的 pipeline 文件名。 |  |
| 捕获以下 Transform 的输出 |  |
| 你可以指定一组 `source pipeline` 和 `source transform` 组合。 |  |

请注意，要添加捕获源，你可以在上下文对话框的 `Preview` 部分使用名为 `Add data probe` 的 Transform Action。

### Transform

| 字段名 | 类型 | 描述 |
|---|---|---|
| sourcePipelineName |  |  |
| 字符串 |  |  |
| 此数据来源的 pipeline 名称 |  |  |
| sourceTransformLogChannelId |  |  |
| 字符串 |  |  |
| 源 Transform 的日志通道 ID |  |  |
| sourceTransformName |  |  |
| 字符串 |  |  |
| 源 Transform 的名称 |  |  |
| sourceTransformCopy |  |  |
| 整数 |  |  |
| 源 Transform 的副本编号 |  |  |
| rowNr |  |  |
| 整数 |  |  |
| 源行号。 |  |  |
| fieldName |  |  |
| 字符串 |  |  |
| 字段名 |  |  |
| fieldType |  |  |
| 字符串 |  |  |
| 字段类型 |  |  |
| fieldFormat |  |  |
| 字符串 |  |  |
| 字段格式 |  |  |
| fieldLength |  |  |
| 整数 |  |  |
| 字段长度 |  |  |
| fieldPrecision |  |  |
| 整数 |  |  |
| 字段精度 |  |  |
| value |  |  |
| 字符串 |  |  |
| 字符串格式的值 |  |  |
