# ![XML Join transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/XJN.svg) XML Join

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Target XML Transform | 向 join 发送目标文档的 transform。 |
| Target XML Field | 包含 XML 结构的字段。 |
| Source XML Transform | 发送需要添加到目标中的 XML 结构到 join 的 transform。 |
| Source XML Field | 包含要添加到目标中的 XML 结构的字段。 |
| XPath Statement | 用于在目标文档中查找开始插入新标签节点的 XPath 语句。 |
| Complex Join | 启用复杂 join 语法的标志，使用 XPath 语句中的占位符。 |
| Join Comparison Field | 包含 XPath 语句中要替换的值的字段。 |
| Result XML field | 将包含结果的字段。 |
| Encoding | XML 头中使用的字符编码以及用于转换 XML 的编码。 |
| Omit XML header | 输出中是否省略 XML 头。 |
| Omit null values from XML | 输出中是否移除 null 值。如果未选择此选项，null 值将作为空元素包含在 XML 输出中，例如 <abc/>。 |
