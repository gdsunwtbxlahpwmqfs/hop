# ![Set field value to a constant transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/constant.svg) Set field value to a constant

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Use variable in constant | 选择在常量中使用变量替换。 |
| Field | 指定包含要替换值的字段。 |
| Replace by value | 指定将替换指定原始字段值的值。 |
| Conversion mask (Date) | 为替换后的字段指定日期格式。 |
| Set empty string? | 指定 Y 以在值为空时允许空字符串；否则使用 null 值。 |
