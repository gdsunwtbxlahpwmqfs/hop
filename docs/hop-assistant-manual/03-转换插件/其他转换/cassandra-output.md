# ![Cassandra Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/cassandraout.svg) Cassandra Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Cassandra connection |  |
| 在此指定要使用的连接名称，可以是固定值或变量表达式。 |  |
| Table to write to |  |
| 指定要写入的表名。 |  |
| Consistency level |  |
| 请参阅 [Cassandra Write consistency levels](https://docs.datastax.com/en/cassandra-oss/3.0/cassandra/dml/dmlConfigConsistency#Writeconsistencylevels) 获取可能的值列表。 |  |
| Commit batch size |  |
| 每次提交中一次更新的行数 |  |
| Batch insert timeout |  |
| 指定在拆分为更小的子批次之前等待批次完全插入的毫秒数。 |  |
| Sub batch size |  |
| 如果因达到批量插入超时而必须拆分批次，请指定子批次大小（行数）。 |  |
| Insert unlogged batches |  |
| 如果你想使用非原子批量写入，请选择此项。 |  |
| Time to live (TTL) |  |
| 指定写入列的时间量。 |  |
| Incoming field to use as the key |  |
| 指定哪个输入字段用作键。 |  |
| Create table |  |
| 选择以在命名表（列族）尚不存在时创建它。 |  |
| Table creation WITH clause |  |
| 指定对表创建 `WITH` 子句的附加内容。 |  |
| Truncate table |  |
| 如果你想在插入传入行之前从命名表中删除任何现有数据，请选择此项。 |  |
| Update table metadata |  |
| 如果你想用尚未存在的输入字段信息更新表元数据，请选择此项。 |  |
| Insert fields not in column meta data |  |
| 如果你想将表元数据插入到任何不存在的输入字段中（相对于默认表验证器），请选择此项。 |  |
