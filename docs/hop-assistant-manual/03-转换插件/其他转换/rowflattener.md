# ![Row Flattener transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/flattener.svg) Row Flattener

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称；此名称在单个 Pipeline 中必须唯一 |
| The field to flatten | 必须扁平化到不同目标字段的字段 |
| Target fields | 字段被扁平化后目标字段的名称 |

## 示例

在下面的示例中，如果您从以下数据集开始……

| Field1 | Field2 | Field3 | Flatten |
|---|---|---|---|
| A | B | C | One |
| Z | Y | X | Two |
| D | E | F | Three |
| W | V | U | Four |

该数据集可以扁平化为以下示例：

| Field1 | Field2 | Field3 | Target1 | Target2 |
|---|---|---|---|---|
| A | B | C | One | Two |
| D | E | F | Three | Four |
