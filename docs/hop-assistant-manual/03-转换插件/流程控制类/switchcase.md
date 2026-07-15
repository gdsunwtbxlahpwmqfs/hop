# ![Switch / Case transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/switchcase.svg) Switch / Case

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | 唯一标识该 transform 的名称。 |
| Field name to switch | 包含用于行路由基准值的字段名称。 |
| Use string contains comparison | 如果勾选，只要在被测字段中的任意位置找到该值，比较结果即为 true。 |
| Case value data type | 此对话框中指定值的数据类型。 |
| Case value conversion mask | 此对话框中指定值的转换掩码（数值/日期值）。 |
| Case value decimal symbol | 此对话框中指定值的小数符号（数值）。 |
| Case value grouping symbol | 此对话框中指定值的分组符号（数值）。 |
| Case values | 在此指定值与目标 transform 的对应关系，每行一对。 |
| Default target transform | 所有不匹配上述任何 case 值的行将被发送到此目标 transform。 |
