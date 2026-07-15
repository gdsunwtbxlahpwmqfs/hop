# ![Set Variables transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/setvariable.svg) Set Variables

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

此 transform 接受一行（且仅一行）数据来设置变量的值。为此 transform 隔离单行数据很重要，一种简单的方法是使用 [Blocking transform](../流程控制类/blockingtransform.md)

变量在 Hop 中没有类型（转换为字符串）。例如，如果使用公式，它可能没有类型，因此必要时请确保转换为字符串（可以使用 TEXT 函数）。

*使用 Set Variable 设置的变量在当前 Pipeline 中不可用。*如果当前 Pipeline 中需要设置的值，这些值必须来自字段。

请注意，变量不能在 Pipeline 之间向上游传递。参数最好向下游传递以避免线程问题。嵌套 Pipeline 在技术上是同一个 Pipeline，因此变量在初始化阶段被继承。

虽然您不能向上游（在嵌套或顺序 Pipeline 中）传递参数和变量，但可以通过 samples 项目中的以下模式将数据行向上传递回 Pipeline：samples/loops/pipeline-executor.hpl

变量可以在一个 Pipeline 中设置，并在 Pipeline executor 循环中的下一个 Pipeline（命名的 Pipeline）中可用。如果您使用 pipeline executor 子 Pipeline，父 Pipeline 不会重启，也不会获取任何设置的变量。要在子 Pipeline 中设置的新变量名称如下面第二列所示。

> **💡 提示:** 此 transform 接受一行（且仅一行）数据来设置变量的值。如果您想从处理多行数据的 Pipeline 中设置变量，请确保在设置变量之前隔离单行数据。

> **⚠️ 警告:** 您不能在同一个 Pipeline 中同时设置和使用变量。由于 Pipeline 中的所有 transform 并行运行，Pipeline 中的 transform 无法使用在同一 Pipeline 中另一个 transform 设置的变量。

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Apply formatting | 设置此选项时，它会根据格式选项格式化值（日期、数字等）。 |
| Field Name | 使用的字段名称 |
| Variable Name | 要设置的变量名称（不带 ${...} 或 %%...%% 符号） |
| Variable scope type a | 定义变量的作用域，可能的选项有： |
| Default value | 为空行设置的值 |
