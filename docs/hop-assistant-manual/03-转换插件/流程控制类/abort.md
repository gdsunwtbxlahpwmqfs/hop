# ![Abort Icon, role="image-doc-icon"](../../assets/images/transforms/icons/abort.svg) Abort

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

| Transform name | 指定画布上 Abort transform 的唯一名称。 |
|---|---|

## 选项

| 选项 | 描述 |
|---|---|
| Abort the running pipeline | 当达到 Abort 阈值时停止 pipeline。 |
| Abort and log as an error | 当 Abort transform 停止 pipeline 时记录错误报告。当父 transform 执行包含 Abort transform 的子 pipeline 时非常有用。 |
| Stop input processing | 当达到 Abort 阈值时停止输入 transform，但仍会处理已检索到的记录。 |
| Abort threshold | 指定在检测到错误后中止 pipeline 的行数。例如，`0` 表示在第一行后停止，`5` 表示在第六行后停止。默认值：`0`。 |

## 日志

Abort transform 提供以下日志选项：

| 选项 | 描述 |
|---|---|
| Abort message | 中止时要写入日志的消息。如果未填写，将使用默认消息。 |
| Always log rows | 始终记录 Abort transform 处理的行。这使得即使 pipeline 的日志级别通常不会记录这些行，也能将它们记录下来。这样您就可以始终在日志中查看导致 pipeline 中止的行。 |
