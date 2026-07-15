# ![Insert / Update transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/insertupdate.svg) Insert / Update

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Commit size | 执行提交前要更改（插入/更新）的行数。 |
| Connection | 数据写入的数据库连接 |
| Don't perform any updates | 如果启用，数据库中的值永远不会被更新；仅执行插入。 |
| Key Lookup table | 允许指定字段值和比较运算符列表。 |
| SQL button | 点击 SQL 以生成用于正确操作的建表和索引 SQL。 |
| Transform name | Transform 名称；此名称在单个 Pipeline 中必须唯一。 |
| Target schema | 数据写入表的 Schema 名称。 |
| Target table | 要执行插入或更新的表名。 |
| Update Fields | 允许指定表中要插入/更新的所有字段，包括键字段。 |
