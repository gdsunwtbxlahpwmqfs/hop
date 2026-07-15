# ![Get Rows from Result transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/rowsfromresult.svg) Get Rows from Result

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

这些行通过 Copy rows to result Transform 或 Pipeline Executor Transform 传递到此 Transform。

您可以输入期望从 Workflow 中前一个 Pipeline 获取的字段的 metadata。

> **💡 提示:** Get Rows From Result 主要因历史原因保留，用于通过 Workflow 构建循环。请查看 [Best Practices](../../index.md) 了解在 Hop 中更好的循环方式。

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 名称，此名称在单个 Pipeline 中必须唯一。 |
| Fieldname | 包含前一个结果的行的字段名称。 |
| Type    a |  |
| Length    a |  |
| Precision    a |  |
