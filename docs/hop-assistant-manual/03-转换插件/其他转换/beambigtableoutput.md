# ![Beam BigTable Output Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-gcp-bigtable-output.svg) Beam Bigtable Output

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
| Key field | 用作 Bigtable 表行键的字段 |
| Columns to set: a | 要写入 Bigtable 表的列列表： |
