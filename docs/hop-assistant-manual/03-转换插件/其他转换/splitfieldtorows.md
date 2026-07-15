# ![Split fields to rows transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/splitfieldtorows.svg) Split fields to rows

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

原始行不会被传递到后续的 transform。

原始字段会保留在每个新行中，以帮助调试或错误处理。

如有需要，可以通过 [Select Values](pipeline/transforms/selectvalues.md) transform 将其移除。

原始字段必须是字符串字段。

必须指定新的字段名称。

如果未指定新行的值类型/格式，新字段将使用与原始字段相同的类型/格式。

## 示例
- 使用分隔符正则表达式按逗号、空格或逗号 + 空格（* n）进行拆分：```[,\s]\s*```

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Field to split | 您要拆分的字段。 |
| Delimiter | 使用的分隔符或分隔符。 |
| Delimiter is a Regular Expression | 用于匹配分隔符的正则表达式。 |
| New field name | 新字段的名称。 |
