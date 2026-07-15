# ![Memory Group By transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/memorygroupby.svg) Memory Group By

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称。此名称在单个 Pipeline 中必须唯一， |
| Always give back a result row | 如果启用此选项，Group By Transform 将始终返回结果行，即使没有输入行。 |
| The fields that make up the group | 指定要分组的字段。点击 Get Fields 添加输入流中的所有字段。 |
| Aggregates | 指定必须聚合的字段、方法以及生成的新字段名称。点击 Get lookup fields 添加输入流中的所有字段。以下是可用的聚合方法： |
