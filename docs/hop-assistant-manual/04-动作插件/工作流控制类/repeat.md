# 循环

## 描述

`Repeat` action 重复执行您选择的 pipeline 或 workflow，直到满足特定条件。
条件为以下之一：

- workflow 中的某个变量被设置或被设置为特定值
- 在指定的重复 workflow 中执行了 End Repeat action

除了 workflow 和 pipeline executor transform 外，Repeat 和 [End Repeat](../其他动作/repeat-end.md) action 让您可以从 workflow 构建循环。它需要一个 workflow 或 pipeline 以及一个运行配置才能使用。该 action 将继续执行指定的 workflow 或 pipeline，直到满足条件：变量被设置、变量被设置为特定可选值，或者在子 workflow 中触发了 End Repeat action。

> **💡 提示:** 如果使用变量名作为要重复的文件，请确保设置了带有文件扩展名的默认参数，以便 Hop 知道它是 pipeline 文件还是 workflow 文件。

> **💡 提示:** 在 pipeline 中以 'valid in the JVM' 范围设置的变量，在重复 pipeline 的上游将无法访问。

## 示例
samples 项目示例：`/loops/repeat-action.hwf` 和 `child-check-set-counter-value.hpl` 运行一个每次执行时递增 `{openvar}COUNTER{closevar}` 变量的 pipeline。如果变量值超过 10，则设置变量 `{openvar}END_LOOP{closevar}`。
Repeat action 检测到此变量后停止循环。由于 workflow 中 `COUNTER` 的变量范围设置为 "Valid in the current workflow"，因此在子流程中设置 `COUNTER` 时，新值会向上游传递。

## 选项

| 选项 | 描述 |
|---|---|
| Workflow action name | Workflow action 的名称。 |
| File to repeat | 要重复执行的 pipeline 或 workflow 的文件名。 |
| Run configuration a | 要使用的 pipeline 或 workflow 运行配置。 |
| Stop repeating when this variable is set | 指定要在停止循环之前检查的变量 |
| Optional variable value | 仅当上方变量被设置且包含此精确值时才停止重复。如果下游通过范围：valid in the Java Virtual Machine 设置的变量值停止 Repeat，您必须以相同方式手动重置该变量，因为在 Repeat 的 Parameters/Variables 选项卡中重置停止变量将不起作用。 |
| Delay in seconds | 在重复执行所选 workflow 或 pipeline 之间添加 x 秒的延迟。 |
| Keep variable values after execution | 此选项在（重复）pipeline 或 workflow 执行后保留变量值，以便注入到下一次迭代中。 |
| Log the execution to a file? | 如果要将所选 workflow 或 pipeline 的重复执行记录到日志文件中，请勾选此项 |
| Base log file name | 要使用的基础日志文件名，下方选项可选择性地附加到该名称之后。 |
| Log file extension | 日志文件使用的扩展名 |
| Add date to filename? | 将当前日期添加到日志文件名中 |
| Add time to filename? | 将当前时间添加到日志文件名中 |
| Add repetition number to filename | 将重复编号添加到日志文件名中 |
| Append to any existing log file | 当当前日志文件已存在时追加而非创建新文件。 |
| Log file update interval? | 更新日志文件的更新间隔（毫秒） |
| Parameters/Variables to set | 您可以在此对话框中指定任何参数或变量的值。 |
