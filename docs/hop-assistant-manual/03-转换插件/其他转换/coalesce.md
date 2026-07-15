# ![Coalesce transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/coalesce.svg) Coalesce

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Considered empty string as null | 该 Transform 可以将空字符串视为 null。 |

## 字段
选择要评估的字段列表，并指定字段输出的字段名称和类型。

| Option | Description |
|---|---|
| Name | 结果字段名，可以覆盖已有字段。 |
| Type | 将值转换为所选数据类型的格式。 |
| Remove | 从流中移除输入字段。 |
| Input fields | 列中列出的输入字段的顺序决定了它们被评估的顺序。 |
