# ![PostgreSQL Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/PGBulkLoader.svg) PostgreSQL Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

> **❗ 重要:** PostgreSQL Bulk Loader 与数据库类型相关联。它会从 hop/lib/jdbc 文件夹中获取 JDBC 驱动。
此 transform 的 JDBC 驱动的有效位置是数据库 plugin 的 lib 目录和主要的 hop/lib 文件夹。它不能与 SHARED_JDBC_FOLDER 变量结合使用。

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Connection | 目标表所在的数据库连接名称。 |
| Target schema | 要写入数据的表的 Schema 名称。 |
| Target table | 目标表的名称。 |
| Load action | Insert、Truncate。 |
| DB Name Override | （可选）用于覆盖此 transform 连接中使用的数据库名称的数据库名。 |
| Enclosure | 在 copy 命令的 `QUOTE AS` 部分中使用的包围字符 |
| Delimiter | 在 copy 命令的 `DELIMITER AS` 部分中使用的分隔符字符 |
| Stop on error | 发生错误时停止此 transform 的执行 |
| Fields to load a | 此表包含要加载数据的字段列表，属性包括： |
