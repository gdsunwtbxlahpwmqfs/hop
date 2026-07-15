# ![Row Generator transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/rowgenerator.svg) Row Generator

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform Name | Transform 的名称，此名称在单个 Pipeline 中必须唯一 |
| Limit | 设置要生成的最大行数 |
| Fields | 此表格用于配置您要生成的行的结构和值（可选）。 |
| Never stop generating rows | 此选项适用于您永远不希望停止正在运行的 Pipeline 的实时用例。 |
| Interval in ms | 生成行之间的间隔（毫秒）。 |
| Current row time field name | 包含当前行生成时间的 Date 字段的*可选*字段名称 |
| Previous row time field name | 包含前一行生成时间的 Date 字段的*可选*字段名称 |
