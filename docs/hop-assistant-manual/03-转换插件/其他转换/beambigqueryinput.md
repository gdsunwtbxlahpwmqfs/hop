# ![BigQuery Input Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-bq-input.svg) BigQuery Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
| Project ID | Google Cloud 项目。 |
| Data set ID | BigQuery 数据集 ID。 |
| Table ID | BigQuery 表 ID。 |
| Query | 输入查询，留空表示读取表中的所有数据。 |
| Return fields selection | 结果字段列表。 |
| BQ Field name | BigQuery 表中的字段名称。 |
| Rename to... (optional) | 给列指定的名称。 |
| Hop data type | 字段数据类型。 |
