# Pipeline

## 描述

`Pipeline` action 在 workflow 中运行先前定义的 pipeline。

此 action 是从您的 workflow 到实际数据处理活动（pipeline）的访问点。

## 用法
一个常见 workflow 的示例包括获取 FTP 文件、检查必要目标数据库表是否存在、运行填充该表的 pipeline，以及如果 pipeline 失败则发送错误日志电子邮件。
在此示例中，Pipeline action 定义要运行哪个 pipeline 来填充表。

另请参见：

- 从 workflow 执行子 workflow 的 [Workflow action](workflow.md)。
- 从 pipeline 执行 workflow 的 [Workflow Executor transform](../../03-转换插件/其他转换/workflow-executor.md)。
- 从 pipeline 执行子 pipeline 的 [Pipeline Executor transform](../../03-转换插件/其他转换/pipeline-executor.md)。

## 选项

### General

| 选项 | 描述 |
|---|---|
| Action name | Action 的名称。 |
| Pipeline | 通过输入路径或点击 Browse 来指定您的 pipeline。 |
| Run Configuration | pipeline 可以在不同类型的 [pipeline 配置](../../06-元数据类型/pipeline-run-config.md)下运行。 |

### Options 选项卡

| 选项 | 描述 |
|---|---|
| Execute for every result row | 为当前 workflow 中先前 pipeline（或 workflow）的每个结果行运行一次 pipeline。 |
| Clear results rows before execution | 确保 pipeline 启动前清除结果行。 |
| Clear results files before execution | 确保 pipeline 启动前清除结果文件。 |
| Wait for remote pipeline to finish | 如果您选择了 Server 作为环境类型，请选择此选项以阻塞 workflow 直到 pipeline 在服务器上运行完成。 |
| Follow local abort to remote pipeline | 如果您选择了 Server 作为环境类型，请选择此选项以远程发送本地中止信号。 |

### Logging 选项卡

默认情况下，如果您不设置日志记录，Qi Hop 将获取生成的日志条目并在 workflow 内创建日志记录。
例如，假设一个 workflow 有三个 pipeline 要运行，而您没有设置日志记录。
这些 pipeline 不会将信息记录到其他文件、位置或特殊配置。
在这种情况下，workflow 运行并将信息记录到其主 workflow 日志中。

在大多数情况下，日志信息可以在 workflow 日志中查看是可接受的。
例如，如果您有加载维度的操作，您希望加载维度运行的日志显示在 workflow 日志中。
如果 pipeline 中有错误，它们将显示在 workflow 日志中。
但是，如果您希望所有日志信息保存在一个地方，则必须设置日志记录。

| 选项 | 描述 |
|---|---|
| Specify logfile | 为运行此 pipeline 指定单独的日志文件。 |
| Name | 指定日志文件的目录和基础名称（例如 C:\logs）。 |
| Extension | 指定文件名扩展（例如 .log 或 .txt）。 |
| Log level | 指定运行 pipeline 的日志级别。 |
| Append logfile | 追加到日志文件而非创建新文件。 |
| Create parent folder | 如果日志文件的父文件夹不存在则创建。 |
| Include date in filename | 将系统日期以 YYYYMMDD 格式添加到文件名中（_20051231）。 |
| Include time in filename | 将系统时间以 HHMMSS 格式添加到文件名中（_235959）。 |

### Parameters 选项卡

*向下传递参数*：在 Parameters 选项卡上，选择 pipeline transform 复选框以 `Pass parameter values to sub pipeline`。参数必须已存在于 pipeline 中（例如在 pipeline 属性中），或者，您可以在 Parameters 选项卡上指定新参数。
Parameters 选项卡允许您覆盖现有参数值或通过将值留空将其设为 NULL。

*向上游传递字段值*：子 pipeline 需要 Copy rows to result transform 才能向上游发送一行。这要求子 pipeline 中存在一行。请注意，行在 workflow 中不存在，但您可以在后续子 pipeline 中使用 Get variables 来使用第一个子 pipeline 的字段值。

如果您想从 pipeline 向上游向 workflow 传递单个值并基于该变量执行操作，请使用 Set variables。在这种情况下，您可以选择范围为 "valid in the parent workflow"。

| 选项 | 描述 |
|---|---|
| Copy results to parameters | 使用 Copy rows to result transform 将先前 pipeline 的结果复制为 pipeline 的参数。 |
| Pass parameter values to sub pipeline | 将 workflow 的所有参数向下传递给子 pipeline。 |
| Parameter | 指定传递给 pipeline 的参数名。 |
| Stream column name | 指定先前 pipeline 传入记录的字段作为参数。 |
| Value | 通过以下操作之一指定 pipeline 参数值： |
| Get Parameters | 获取 pipeline 已关联的现有参数。 |
