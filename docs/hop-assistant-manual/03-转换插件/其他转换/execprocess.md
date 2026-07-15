# ![Execute a process transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/execprocess.svg) Execute a process

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称；该名称在单个 Pipeline 中必须唯一 |
| Process field | 数据流中定义要启动的进程（要启动的 shell 脚本/批处理文件）的字段名。 |
| Fail if not success | 勾选此选项意味着如果退出状态不为零，Transform 将失败。 |
| Output line delimiter | 如果不定义行分隔符，所有返回的行将被折叠成一个没有行分隔符的单一字符串。 |
| Result fieldname | 在此指定添加到 Pipeline 输出流的结果字段名（STRING）。 |
| Error fieldname | 在此指定添加到 Pipeline 输出流的错误字段名（STRING）。 |
| Exit value | 在此指定添加到 Pipeline 输出流的退出字段名（INTEGER）。 |
