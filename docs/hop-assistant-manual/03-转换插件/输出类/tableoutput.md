# ![Table Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/tableoutput.svg) Table Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

Table Output 等同于 DML 操作符 INSERT。

此 Transform 提供目标表的配置选项以及许多与维护和/或性能相关的选项，如 Commit Size 和 Use batch update for inserts。

如果您有一个包含标识列的数据库表，并且正在插入记录，作为插入的一部分，JDBC 驱动程序通常会返回执行插入时使用的自动生成键。

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Connection | 写入数据的数据库连接 |
| Target Schema | 要写入数据的表的 Schema 名称。 |
| Target table | 写入数据的目标表名称。 |
| Commit size | 使用事务将行插入数据库表。 |
| Truncate table | 如果您希望在第一行插入表之前截断表，请选择 |
| Truncate on first row | 如果您希望在 Transform 接收第一行时截断表，请选择 |
| Ignore insert errors | 使 Hop 忽略所有插入错误，如主键冲突。 |
| Specify database fields | 启用此选项以在 Database fields 选项卡中指定字段。 |
| Automatically update table structure | 根据传入的数据流自动管理表结构。 |
| Always drop and recreate table | 每次执行时删除并重新创建表。 |
| Add columns | 将传入流中存在但表中不存在的列添加到表中。 |
| Drop non-existing columns | 从表中删除传入流中不存在的列。 |
| Change column data types | 更改列数据类型以匹配传入流。 |
| Partition data over tables a | 用于将数据拆分到多个表。 |
| Use batch update for inserts | 如果您要使用批量插入，请启用。 |
| Is the name of the table defined in a field? | 使用这些选项将数据拆分到一个或多个表上；目标表的名称在您指定的字段中定义。 |
| Field that contains name of table | 当启用 "Is the name of the table defined in a field?" 选项时，在此处输入要使用的字段名称。 |
| Store the table name field | 当启用 "Is the name of the table defined in a field?" 选项时，您可以选择是否将该字段写入表中。 |
| Return auto-generated key | 如果您希望获取向表中插入行时生成的键，请启用 |
| Name of auto-generated key field | 指定输出行中包含自动生成键的新字段名称 |
| SQL | 自动生成创建输出表的 SQL |
