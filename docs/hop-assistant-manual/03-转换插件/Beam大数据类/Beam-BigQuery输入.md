# Beam BigQuery 输入（BigQuery Input）

BigQuery 输入转换从 Google Cloud BigQuery 表中读取数据。它既可以运行在本地 Hop 引擎上（使用 BigQuery Java 客户端），也可以运行在所有 Beam 引擎上。

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
| 项目 ID（Project ID） | Google Cloud 项目。 |
| 数据集 ID（Data set ID） | BigQuery 数据集 ID。 |
| 表 ID（Table ID） | BigQuery 表 ID。 |
| 查询（Query） | 输入查询语句，留空表示读取表中所有数据。 |
| 返回字段选择（Return fields selection） | 结果字段列表。 |
| BQ 字段名（BQ Field name） | BigQuery 表中的字段名。 |
| 重命名为……（可选）（Rename to... (optional)） | 为列指定的名称。 |
| Hop 数据类型（Hop data type） | 字段数据类型。 |
