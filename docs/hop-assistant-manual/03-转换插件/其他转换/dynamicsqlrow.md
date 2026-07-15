# ![Dynamic SQL row transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/dynamicsqlrow.svg) Dynamic SQL row

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称；该名称在单个 Pipeline 中必须唯一 |
| Connection | 选择用于查找的数据库连接 |
| SQL field name | 指定包含要执行 SQL 的字段 |
| Number of rows to return | 指定要返回的记录数。0 表示返回所有行 |
| Outer Join | - false：不返回未找到内容的行 - true：至少返回一个源行，其余为 NULL |
| Replace variables | 如果你想在 SQL 中使用变量，例如 {openvar}table_name{closevar}，需要勾选此选项。 |
| Query only on parameters change | 如果你的 SQL 语句变化不大，勾选此选项以减少物理数据库查询的次数。 |
| Template SQL | 在 Hop 中元数据和数据是分离的，因此你需要在模板 SQL 中指定元数据部分（字段名和类型）。 |
