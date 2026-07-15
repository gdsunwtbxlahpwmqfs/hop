# ![Pipeline Executor transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/pipelineexecutor.svg) Pipeline Executor

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法
Pipeline Executor Transform 默认逐行将数据发送到子 Pipeline。可以在 "Row grouping" 选项卡中更改此默认行为。

可选地，您可以在 Pipeline 中使用 "Get rows from result" Transform 从子 Pipeline 获取先前的行。您不需要在 Get rows Transform 中定义任何字段即可检索所有数据行字段。

您可以通过指定要执行的 Pipeline 名称为每行运行相同的 Pipeline，或者从传入字段（例如来自表）接受 Pipeline 名称。
您可以启动多个 Transform 副本以并行运行。

*参数*：可以将子 Pipeline 参数映射到当前 Pipeline 中的字段。如果启用 "Inherit all variables from pipeline" 选项，父 Pipeline 中定义的所有参数/变量都会传递到子 Pipeline。但是，只有在 Parameters 选项卡上定义的参数才会按数据输入行设置到 Pipeline 中。

*输出 hop 连接器选项*：如果为 Pipeline Executor 选择了错误的输出选项，可能不会返回预期的数据。

- *此输出将包含执行结果*：返回执行统计信息，不限制输出中的字段、变量或参数。建议至少检查子 Pipeline 中是否存在问题，使用 ExecutionResult、ExecutionExitStatus 或 ExecutionNrErrors 字段。

- *此输出将包含执行后的结果行*：输出由子 Pipeline 复制到内存的行集（例如通过 Copy rows to result Transform）。使用 Pipeline Executor Transform 中的 "Result rows" 选项卡指定您期望从子 Pipeline 接收的字段。此选项也是下游设置变量和上游使用变量的必要选项（如果在您的范围内有效）。

- *此输出将包含执行后的结果文件名*：输出包含已复制到结果中的文件名的行集（例如通过 Text file input^ Transform 的 Content 选项卡中的 Add filenames to result）。

- *此输出将包含 Executor Transform 输入数据的副本*：输出 Pipeline Executor Transform 接收到的行集。

- *Transform 的主输出*：输出模拟 Pipeline Executor Transform 输入的行集。

根据您的需求，Pipeline Executor Transform 可以配置为以下任何方式运行：

- 默认情况下，指定的 Pipeline 将为每个输入行执行一次。您可以使用输入行设置参数和变量。Executor Transform 然后将此行以结果行的形式传递给 Pipeline。

- 您还可以基于字段中的值传递一组记录，以便当字段值动态变化时，执行指定的 Pipeline。在这些情况下，行组中的第一行用于设置 Pipeline 中的参数或变量。

- 您可以启动此 Transform 的多个副本来辅助并行 Pipeline 处理。

另请参阅：

- 从 Workflow 执行子 Workflow 的 [Workflow action](../../04-动作插件/工作流控制类/workflow.md)。
- 从 Workflow 执行 Pipeline 的 [Pipeline action](../../04-动作插件/工作流控制类/pipeline.md)。
- 从 Pipeline 执行 Workflow 的 [Workflow Executor transform](workflow-executor.md)。
- [Loops in Qi Hop](../../16-HowTo指南/loops-in-apache-hop.md) 操作指南。

示例（samples 项目）：

- loops/pipeline-executor.hpl

## 选项

### General

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Pipeline a | 使用此部分指定要执行的 Pipeline。 |
| Pipeline from field? | 启用以在输入流的字段中指定 Pipeline 文件名 |
| Pipeline field | 当启用上一选项时，您可以指定在运行时包含 Pipeline 文件名的字段。文件名可以包含变量。 |
| Run configuration | 指定用于执行的 Pipeline Run Configuration。 |

### Parameters 选项卡

在此选项卡中，您可以指定使用哪个字段来设置某个参数或变量值。如果多行传递到 Workflow，则取第一行设置参数或变量。
> **💡 提示:** 如果您保留 "Inherit all variables from pipeline" 选项为勾选状态（默认为勾选），当前 Pipeline 中定义的所有变量都会传递到子 Pipeline。

您只能向下游传递*参数和变量*。除非 Pipeline 是新启动的，否则无法在 Pipeline 之间传递参数/变量。例如，当每个命名 Pipeline 在 Pipeline Executor 中逐行启动时，您可以在 Pipeline 之间传递参数/变量。

虽然您无法向上游传递参数和变量（在嵌套或顺序 Pipeline 中），但可以通过以下模式将数据行向上游传回 Pipeline。参见项目：samples/loops/pipeline-executor.hpl

- *父 Pipeline Executor* 在 "Result rows" 选项卡下指定子 Pipeline 行中定义的行字段名。父 Pipeline Executor 的输出选项为 "result rows after execution"。

- *子 Pipeline*：生成一个数据行，其字段名和类型与父 Pipeline Executor 的 "Results rows" 选项卡中定义的相同。子 Pipeline 的最后一个 Transform 是 "copy rows to result"。

请记住，所有参数必须在每个 Pipeline 或 Workflow 中至少定义一次（在编辑 Pipeline/Workflow 属性中）。

| 选项 | 描述 |
|---|---|
| Variable / Parameter name | Parameters 选项卡允许您定义或向 Pipeline 传递 Hop 变量。 |
| Field to use | 指定使用哪个字段来设置某个参数或变量值。 |
| Static input value | 代替使用字段，您可以在此指定一个静态值。 |

选项卡右下角的 `Get Parameters` 按钮将插入指定 Pipeline 中所有已定义的参数及其描述。

选项卡右下角的 `Map Parameters` 按钮允许您将当前 Pipeline 中的字段映射到子 Pipeline 中的参数。

### Row Grouping 选项卡

在此选项卡中，您可以指定以结果行形式传递给 Pipeline 的输入行数量。
您可以在 Pipeline 中的 Get rows from result Transform 中使用这些结果行。

| 选项 | 描述 |
|---|---|
| The number of rows to send to the pipeline | 每隔 X 行，Pipeline 将被执行，这 X 行将传递给 Pipeline |
| Field to group rows on | 只要字段值保持不变，行就会累积在一个组中。 |
| The time to wait collecting rows before execution | 这是 Transform 在执行 Pipeline 之前累积行所花费的时间（以毫秒为单位）。 |

### Execution Results 选项卡

您可以指定结果字段以及将它们发送到哪个 Transform。
如果不需要某个结果，只需留空输入字段。

| 选项 | 描述 | 默认值 |
|---|---|---|
| Target transform for the execution results |  |  |
| 使用下拉菜单在当前 Pipeline 中选择一个 Transform 作为目标 Transform，以接收指定 Pipeline 的结果。 |  |  |
| N/A |  |  |
| Execution time (ms) |  |  |
| 指定 Pipeline 执行时间的字段名。 |  |  |
| ExecutionTime |  |  |
| Execution result |  |  |
| 指定 Pipeline 执行结果的字段名。 |  |  |
| ExecutionResult |  |  |
| Number of errors |  |  |
| 指定 Pipeline 执行期间错误数量的字段名。 |  |  |
| ExecutionNrErrors |  |  |
| Number of rows read |  |  |
| 指定 Pipeline 执行期间读取总行数的字段名。 |  |  |
| ExecutionLinesRead |  |  |
| Number of rows written |  |  |
| 指定 Pipeline 执行期间写入总行数的字段名。 |  |  |
| ExecutionLinesWritten |  |  |
| Number of rows input |  |  |
| 指定 Pipeline 执行期间输入总行数的字段名。 |  |  |
| ExecutionLinesInput |  |  |
| Number of rows output |  |  |
| 指定 Pipeline 执行期间输出总行数的字段名。 |  |  |
| ExecutionLinesOutput |  |  |
| Number of rows rejected |  |  |
| 指定 Pipeline 执行期间拒绝总行数的字段名。 |  |  |
| ExecutionLinesRejected |  |  |
| Number of rows updated |  |  |
| 指定 Pipeline 执行期间更新总行数的字段名。 |  |  |
| ExecutionLinesUpdated |  |  |
| Number of rows deleted |  |  |
| 指定 Pipeline 执行期间删除总行数的字段名。 |  |  |
| ExecutionLinesDeleted |  |  |
| Number of files retrieved |  |  |
| 指定 Pipeline 执行期间检索文件总数的字段名。 |  |  |
| ExecutionFilesRetrieved |  |  |
| Exit status |  |  |
| 指定 Pipeline 执行退出状态的字段名。 |  |  |
| ExecutionExitStatus |  |  |
| Execution logging text |  |  |
| 指定 Pipeline 执行日志文本的字段名。 |  |  |
| ExecutionLogText |  |  |
| Log channel ID |  |  |
| 指定 Pipeline 执行期间使用的日志通道 ID 的字段名。 |  |  |
| ExecutionLogChannelID |  |  |

### Result Rows 选项卡

在 "Result rows" 选项卡中，您可以指定此 Pipeline 预期结果行的布局，以及执行后将它们发送到哪个 Transform。

| 选项 | 描述 |
|---|---|
| Target transform for result rows |  |
| 使用下拉菜单在当前 Pipeline 中选择一个 Transform 作为目标 Transform。 |  |
| Field name |  |
| 指定字段的名称。 |  |
| Data type |  |
| 使用下拉菜单指定字段的数据类型，如 number、date 或 string。 |  |
| Length |  |
| 如果适用，指定数据类型的长度。 |  |
| Precision |  |
| 如果适用，指定要使用的精度。 |  |

请注意，此 Transform 将验证结果行字段的数据类型是否与指定的完全一致。
如果存在差异，将抛出错误。

### Result Files 选项卡

在此您可以指定将 Pipeline 执行的结果文件发送到哪里。

| 选项 | 描述 |
|---|---|
| Target transform for result files information |  |
| 使用下拉菜单在 Pipeline 中选择一个 Transform 作为目标 Transform。 |  |
| Result file name field |  |
| 指定结果文件的字段名称。 |  |
