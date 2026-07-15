# ![Unique Rows transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/uniquerows.svg) Unique Rows

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform Name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Add counter to output? | 勾选此选项可在流中添加一个计数字段。 |
| Counter field | 定义计数字段的名称。 |
| Redirect duplicate row | 将重复行作为错误处理，并将行重定向到 transform 的错误流。 |
| Error Description | 设置检测到重复行时显示的错误处理描述。 |
| Fields to compare table | 指定您要强制唯一性的字段名称，或点击 Get 从输入流中插入所有字段。您可以通过将 Ignore case 标志设置为 Y 来选择忽略大小写。 |
