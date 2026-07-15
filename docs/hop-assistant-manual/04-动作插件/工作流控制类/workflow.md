# Workflow

## 描述

`Workflow` action 执行一个先前定义的 workflow 作为当前 workflow 的子 workflow。

为方便使用，也可以在对话框中按 New Workflow 按钮创建一个新 workflow。

使用 Workflow action 执行先前定义的 workflow。

这使您能够进行"功能分解"。也就是说，您使用它们将 workflow 拆分为更易于管理的单元。

例如，您不应该使用一个包含 500 个 action 的 workflow 来编写数据仓库加载。

最好创建较小的 workflow 并进行组合。

另请参见：

- 从 workflow 执行 pipeline 的 [Pipeline action](workflow/actions/pipeline.md)。
- 从 pipeline 执行 workflow 的 [Workflow Executor transform](pipeline/transforms/workflow-executor.md)。
- 从 pipeline 执行子 pipeline 的 [Pipeline Executor transform](pipeline/transforms/pipeline-executor.md)。

## 选项

### 主 workflow 选项

| 选项 | 描述 |
|---|---|
| Action name | Action 的名称。 |
| Workflow Filename a | 指定要执行的 workflow 的 XML 文件名。点击浏览本地文件。 |
| Run configuration a | 用于此 workflow action 的 [workflow 运行配置](metadata-types/workflow-run-config.md)。 |

### Options 选项卡

| 选项 | 描述 | 默认值 |
|---|---|---|
| Execute for every input row? | 实现循环；如果先前的 workflow action 返回一组结果行，则 workflow 对找到的每一行执行一次。 |  |
| Wait for the remote workflow to finish? | 启用以阻塞直到 Hop Server 上的 workflow 完成 | true |

### Logging 设置选项卡

默认情况下，如果您不设置日志记录，Hop 将获取生成的日志动作并在 workflow 内创建日志记录。

例如，假设一个 workflow 有三个 pipeline 要运行，而您没有设置日志记录。

这些 pipeline 不会将日志信息输出到其他文件、位置或特殊配置。

在这种情况下，workflow 执行并将日志信息放入其主 workflow 日志中。

在大多数情况下，日志信息可以在 workflow 日志中查看是可接受的。

例如，如果您有加载维度的操作，您希望加载维度运行的日志显示在 workflow 日志中。

如果 pipeline 中有错误，它们将显示在 workflow 日志中。

但是，如果您希望所有日志信息保存在一个地方，则必须设置日志记录。

| 选项 | 描述 |
|---|---|
| Specify logfile? | 启用以为此 workflow 的执行指定单独的日志文件 |
| Name of logfile | 日志文件的目录和基础名称；例如 `C:\logs` |
| Extension of logfile | 文件名扩展；例如 log 或 txt |
| Loglevel | 指定执行 workflow 的日志级别。有关更多详细信息，请参见[日志记录](logging/logging-basics.md)。 |
| Append logfile? | 启用以追加到日志文件而非创建新文件 |
| Create parent folder | 如果日志文件的父文件夹不存在则创建 |
| Include date in logfile? | 将系统日期以 YYYYMMDD 格式添加到文件名中（例如 20051231）。 |
| Include time in logfile? | 将系统时间以 HHMMSS 格式添加到文件名中（例如 235959）。 |

### Parameters 选项卡

指定要传递给子 workflow 的参数：

| 选项 | 描述 |
|---|---|
| Copy results to parameter | 先前 workflow 或 pipeline 的结果作为参数传递给此 workflow action |
| Pass parameter values down to the sub-workflow | 启用此选项以将 workflow 的所有参数传递给子 workflow。 |
| Parameters | 指定将传递给 workflow 的参数名。 |
| Stream column name | 允许您将结果集传入记录的字段作为参数捕获。 |
| Value |  |
