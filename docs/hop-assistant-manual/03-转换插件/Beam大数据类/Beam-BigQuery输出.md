# Beam BigQuery 输出（BigQuery Output）

BigQuery 输出转换将数据写入 Google Cloud BigQuery 表。在本地 Hop 引擎上，它使用 BigQuery Java 客户端的流式 `insertAll` API（每批最多 500 行）；在 Beam 引擎上，它使用 Beam 原生的 BigQuery 接收器（sink）。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ✅ 支持 |
| Flink | ✅ 支持 |
| Dataflow | ✅ 支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 转换的名称，在单个管道中必须唯一。 |
| 项目 ID（Project ID） | Google Cloud Platform 项目。留空则使用 `GOOGLE_APPLICATION_CREDENTIALS` 中的应用默认项目。 |
| 数据集 ID（Data set ID） | BigQuery 数据集 ID，必须已存在。 |
| 表 ID（Table ID） | BigQuery 表 ID。 |
| 需要时创建表（Create table if needed） | 当表尚不存在时创建它。架构由输入行元数据派生（Hop 类型 → BigQuery `STANDARD_SQL` 类型）。默认：true。 |
| 清空表（Truncate table） | 写入前清空表。在 Hop 引擎上会执行 `TRUNCATE TABLE` DDL——无需成本，不占用 DML 配额，并保留架构/分区/聚簇。 |
| 表非空时失败（Fail if the table is not empty） | 当目标表已有数据行时拒绝运行。与"清空表"选项配合可实现幂等加载。 |

> **注意：** BigQuery 流式插入有其独立的配额（每次 `insertAll` 请求最多 10,000 行 / 10 MB，项目默认每秒 100,000 行）——与 DML 配额分开计算。数据行几乎立即可供 `SELECT` 查询，但在迁移到托管存储之前会在流式缓冲区中停留最多约 90 分钟；在此之前对这些行执行 DML 会被拒绝，但 DDL（包括由"清空表"选项执行的 `TRUNCATE TABLE`）则没有问题。
