# ![Stream Schema Merge transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/streamschemamerge.svg) Stream Schema Merge

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Transform | 要合并的 transform 列表。两个或多个输入流中具有相同字段名的字段将被合并为一个统一字段（如果数据类型相同则保留原类型，如果不同则转换为 `String`）。所有输入流中的所有字段都将添加到输出流中。 |
