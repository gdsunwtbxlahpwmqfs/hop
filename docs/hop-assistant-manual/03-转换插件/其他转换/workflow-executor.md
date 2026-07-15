# ![Workflow Executor transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/workflowexecutor.svg) Workflow Executor

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

默认情况下，指定的 workflow 将为每个输入行执行一次（可以在 Row Grouping 选项卡中更改此设置）。

来自数据行的字段可用于设置参数和变量，并以结果行的形式传递给 workflow。请注意，所有参数必须至少在每个 pipeline 或 workflow 中定义一次（Edit pipeline/workflow 属性）。当您从 Parameters 选项卡将字段/参数/变量发送到 workflow executor 时，您就是在传递参数。

您还可以允许基于字段中的值（当值变化时执行 workflow）或基于时间来传递一组记录。在这些情况下，使用组的第一行或多行来设置 workflow 中的参数或变量。

可以启动此 transform 的多个副本以实现并行 workflow 处理。

另请参阅：

- 从 workflow 中执行子 workflow 的 [Workflow action](workflow/actions/workflow.md)。
- 从 workflow 中执行 pipeline 的 [Pipeline action](workflow/actions/pipeline.md)。
- 从 pipeline 中执行子 pipeline 的 [Pipeline Executor transform](pipeline/transforms/pipeline-executor.md)。
- [Loops in Qi Hop](how-to-guides/loops-in-apache-hop.md) 操作指南。

示例（samples 项目）

- loops/workflow-executor.hpl

## 选项

### 常规

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Workflow | 使用此选项指定存储在文件中的 workflow（.hwf 文件） |
| Run configuration | 指定执行时使用的 Workflow Run Configuration。 |

### Parameters 选项卡

在此选项卡中，您可以指定使用哪个字段来设置某个参数或变量值。
如果您指定了要使用的输入字段，则不使用静态输入值。
如果将多行传递给 workflow，则取第一行来设置参数或变量。

选项卡右下角有一个按钮，用于插入指定 workflow 中所有已定义的参数。
信息参数的描述将插入到静态输入值字段中。

> **💡 提示:** 如果您保持"Inherit all variables from pipeline"选项勾选（默认勾选），当前 pipeline 中定义的所有变量都将传递给子 workflow。

选项卡右下角的 `Get Parameters` 按钮将插入指定 workflow 中所有已定义的参数及其描述。

选项卡右下角的 `Map Parameters` 按钮允许您将当前 pipeline 中的字段映射到子 workflow 中的参数。

### Row Grouping 选项卡

在此选项卡中，您可以指定以结果行的形式传递给 workflow 的输入行数量。

使用以下方法之一指定如何对结果行进行分组：

- 特定行数。
- 特定字段。
- 指定持续时间。

您可以在 pipeline 或 workflow action 中使用结果行，也可以在 pipeline 中使用 *Get rows from result* action 直接检索记录。

要访问 *Field to group rows on* 或 *Duration time when collecting rows* 选项，请移除 *Number of rows to send to pipeline* 选项中的默认值（1）。

| 选项 | 描述 |
|---|---|
| *Number of rows to send to pipeline* |  |
| 指定一个数字。每 *n* 行之后，workflow 将被执行，这些 *n* 行将被传递给 pipeline。默认值：1 |  |
| *Field to group rows on* |  |
| 指定用于行分组的字段。只要字段值保持不变，行将在一个组中累积。 |  |
| *Duration time when collecting rows* |  |
| 以毫秒为单位指定持续时间。这是 transform 在执行 workflow 之前用于累积行的时间。 |  |

请注意，您只能指定一种分组方法。

### Execution Results 选项卡

您可以指定结果字段以及将它们发送到哪个 transform。
如果不需要某个结果，只需留空输入字段。

| 选项 | 描述 | 默认值 |
|---|---|---|
| Target transform for the execution results |  |  |
| 使用下拉菜单在 pipeline 中选择一个 transform 作为目标 transform，以接收 workflow 的结果。 |  |  |
| N/A |  |  |
| Execution time (ms) |  |  |
| 指定 workflow 执行时间的字段名称。 |  |  |
| ExecutionTime |  |  |
| Execution result |  |  |
| 指定 workflow 执行结果的字段名称。 |  |  |
| ExecutionResult |  |  |
| Number of errors |  |  |
| 指定 workflow 执行期间错误数量的字段名称。 |  |  |
| ExecutionNrErrors |  |  |
| Number of rows read |  |  |
| 指定 workflow 执行期间读取总行数的字段名称。 |  |  |
| ExecutionLinesRead |  |  |
| Number of rows written |  |  |
| 指定 workflow 执行期间写入总行数的字段名称。 |  |  |
| ExecutionLinesWritten |  |  |
| Number of rows input |  |  |
| 指定 workflow 执行期间输入总行数的字段名称。 |  |  |
| ExecutionLinesInput |  |  |
| Number of rows output |  |  |
| 指定 workflow 执行期间输出总行数的字段名称。 |  |  |
| ExecutionLinesOutput |  |  |
| Number of rows rejected |  |  |
| 指定 workflow 执行期间拒绝总行数的字段名称。 |  |  |
| ExecutionLinesRejected |  |  |
| Number of rows updated |  |  |
| 指定 workflow 执行期间更新总行数的字段名称。 |  |  |
| ExecutionLinesUpdated |  |  |
| Number of rows deleted |  |  |
| 指定 workflow 执行期间删除总行数的字段名称。 |  |  |
| ExecutionLinesDeleted |  |  |
| Number of files retrieved |  |  |
| 指定 workflow 执行期间检索总文件数的字段名称。 |  |  |
| ExecutionFilesRetrieved |  |  |
| Exit status |  |  |
| 指定 workflow 执行退出状态的字段名称。 |  |  |
| ExecutionExitStatus |  |  |
| Execution logging text |  |  |
| 指定 workflow 执行日志文本的字段名称。 |  |  |
| ExecutionLogText |  |  |
| Log channel ID |  |  |
| 指定 workflow 执行期间使用的日志通道 ID 的字段名称。 |  |  |
| ExecutionLogChannelID |  |  |

### Result Rows 选项卡

在"Result rows"选项卡中，您可以指定此 workflow 预期结果行的布局，以及执行后将它们发送到哪个 transform。

workflow executor 会对我们在该选项卡中声明为希望接收输出的字段进行一致性检查。检查将确保我们需要的字段确实存在于结果流中，并且每个字段的类型确实是我们期望的类型。如果有任何差异，将抛出错误。该错误将为您提供完整的画面，说明哪些字段缺失和/或哪些字段由于考虑了错误的数据类型而被声明。

| 选项 | 描述 |
|---|---|
| Target transform for result rows |  |
| 使用下拉菜单在 pipeline 中选择一个 transform 作为目标 transform。 |  |
| Field name |  |
| 指定字段的名称。 |  |
| Data type |  |
| 使用下拉菜单指定字段的数据类型，例如数字、日期或字符串。 |  |
| Length |  |
| 如果适用，指定字段的长度。 |  |
| Precision |  |
| 如果适用，指定要使用的精度。 |  |

*注意*：请记住，当前此 transform 始终会返回一个结果行，即使子 workflow 中启动的 pipeline 没有返回任何结果。在这种情况下，您将获得的结果行将仅包含由此 transform 输入流提供的字段。

### Result Files 选项卡

此处您可以指定将 workflow 执行的结果文件发送到哪里。

| 选项 | 描述 |
|---|---|
| Target transform for result files information |  |
| 使用下拉菜单在 pipeline 中选择一个 transform 作为目标 transform。 |  |
| Result file name field |  |
| 指定结果文件的字段名称。 |  |
