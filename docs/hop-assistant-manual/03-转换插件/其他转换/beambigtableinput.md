# ![Beam BigTable Input Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-gcp-bigtable-input.svg) Beam Bigtable Input

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
| Project ID | Google Cloud 项目。 |
| Instance ID | Bigtable 实例 ID。 |
| Table ID | Bigtable 表 ID。 |
| Output key field name | 存储键（行键）的输出字段名称。 |
| Source columns to read | 在此表中指定要从 Bigtable 读取的列。 |
| Source column (qualifier) | 要读取的特定 Bigtable 列限定符。 |
| Target Hop data type | Hop 中对应字段的数据类型。 |
| Target field name | Hop 中存储列值的目标字段名称。 |
