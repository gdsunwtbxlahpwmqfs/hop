# ![Write to log transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/writetolog.svg) 写入日志

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

> **💡 提示:** 将（未加密的）密码或其他敏感信息的变量或字段写入 Hop 日志系统时要小心！

如果在 Write to log Fields 列表中没有添加任何字段，并且在指定了 Basic 日志级别时，它将所有字段输出到日志。如果向 Fields 列表添加了字段，则只会输出这些字段。参数和变量必须在 Log message 部分中指定。

必须设置日志级别。如果要打印变量，请将 Log detail 级别设置为 Basic。

在 Log Message 部分打印参数或变量的示例：``COUNTER: {openvar}myCounter{closevar}``

如果不存在包含字段的数据行，则记录字段、参数或变量将不起作用。您可以使用 "Generate rows" transform 创建一个虚拟行来打印变量或参数。

> **💡 提示:** 如果错误导致任何内容都无法写入日志，您可以禁用失败的 Hop，在失败之前添加日志记录，然后重新运行。

## 选项

| 选项 | 描述 |
|---|---|
| Transform Name | Transform 的名称，此名称在单个 pipeline 中必须唯一。 |
| Log level | 要使用的日志级别。 |
| Print header | 是否打印数据值的列名。 |
| Limit rows | 限制由参数 "Nr. of rows to print" 给出的行数。 |
| Nr. of rows to print | 勾选 "limit rows" 选项时要打印的行数。 |
| Write to log | 日志行中使用的文本。 |
| Fields | 应写入日志的字段数据。 |

## 日志输出示例

```bash
2020/05/14 12:30:52 - Write to log.0 -
2020/05/14 12:30:52 - Write to log.0 - ----------> Linenr 1----------------------------
2020/05/14 12:30:52 - Write to log.0 - test log
2020/05/14 12:30:52 - Write to log.0 -
2020/05/14 12:30:52 - Write to log.0 -  3
2020/05/14 12:30:52 - Write to log.0 -  4
2020/05/14 12:30:52 - Write to log.0 -
2020/05/14 12:30:52 - Write to log.0 - ====================
2020/05/14 12:30:52 - Write to log.0 -
2020/05/14 12:30:52 - Write to log.0 - ----------> Linenr 2----------------------------
2020/05/14 12:30:52 - Write to log.0 - test log
2020/05/14 12:30:52 - Write to log.0 -
2020/05/14 12:30:52 - Write to log.0 -  3
2020/05/14 12:30:52 - Write to log.0 -  4
2020/05/14 12:30:52 - Write to log.0 -
2020/05/14 12:30:52 - Write to log.0 - ====================
```
