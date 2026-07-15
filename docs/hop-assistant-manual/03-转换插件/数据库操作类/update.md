# ![Update transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/update.svg) Update

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Commit size | 执行提交前要更新的行数。 |
| Use batch updates? | 允许使用批量更新。 |
| Skip lookup | 跳过行查找。 |
| Ignore lookup failure? | 允许 transform 跳过查找失败。 |
| Flag field (key found) | 包含是否找到键的标志的字段。 |
| Connection | 数据写入的数据库连接。 |
| Key Lookup table | 允许您指定字段值和比较器的列表。 |
| SQL button | 点击 SQL 可生成用于创建表和索引以确保正确操作的 SQL。 |
| Transform name | Transform 的名称；此名称在单个 pipeline 中必须唯一。 |
| Target schema | 数据写入表的 schema 名称。 |
| Target table | 要执行更新的表名。 |
| Update Fields | 允许您指定表中要更新的所有字段。 |
