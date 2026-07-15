# ![BigQuery Output Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-bq-output.svg) BigQuery Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
| Project ID | Google Cloud Platform 项目。留空则使用 `GOOGLE_APPLICATION_CREDENTIALS` 中的应用默认项目。 |
| Data set ID | BigQuery 数据集 ID。必须已存在。 |
| Table ID | BigQuery 表 ID。 |
| Create table if needed | 当表尚不存在时创建表。模式从输入行元数据派生（Hop 类型 → BigQuery `STANDARD_SQL` 类型）。默认值：true。 |
| Truncate table | 写入前清空表。在 Hop 引擎上这会运行 `TRUNCATE TABLE` DDL — 免费，不消耗 DML 配额，保留模式/分区/聚类。 |
| Fail if the table is not empty | 如果目标表已有行，则拒绝运行。与 `Truncate table` 配合使用可实现幂等加载。 |

> **📝 注意:** BigQuery 流式插入有其自身的配额（每个 `insertAll` 请求 10 000 行 / 10 MB，项目默认 100 000 行/秒）— 与 DML 配额分开。行几乎立即可通过 `SELECT` 查询，但在迁移到托管存储之前会在流式缓冲区中保留最多约 90 分钟；在此之前对这些行执行 DML 会被拒绝，但 DDL（包括 Truncate 选项中的 `TRUNCATE TABLE`）没有问题。
