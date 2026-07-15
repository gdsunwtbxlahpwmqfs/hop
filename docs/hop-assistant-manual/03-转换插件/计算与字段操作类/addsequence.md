# ![Add Sequence Icon, role="image-doc-icon"](../../assets/images/transforms/icons/addsequence.svg) Add Sequence

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | 此 transform 在 pipeline 工作区中显示的名称。 |
| Name of value | 添加到数据流中的新序列值的名称。 |
| Use DB to generate the sequence | 如果希望序列由数据库序列驱动，请启用此项，然后设置以下参数：Connection name、Schema name（可选）、Sequence name。 |
| Connection name | 数据库序列所在的连接名称。 |
| Schema name (optional) | 表的 schema 名称。 |
| Sequence name | 数据库序列的名称。 |
| Use a pipeline counter to generate the sequence | 如果希望序列由 Hop 生成，请启用此项，然后设置以下参数：Counter name（可选）、Start at、Increment by、Maximum value。 |
| Counter name (optional) | 如果 pipeline 中多个 transform 生成相同的值名称，此选项允许您指定要关联的计数器名称。 |
| Start at | 序列的起始值。 |
| Increment by | 序列递增或递减的步长。 |
| Maximum value | 序列达到此值后将返回到 Start At 值。 |
