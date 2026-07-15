# ![Column exists transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/columnexists.svg) Column exists

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称；此名称在单个 pipeline 中必须唯一。 |
| Connection | 要使用的数据库连接。 |
| Schema name | （可选）要检查列所在表的 schema 名称。 |
| Table name | 要检查列所在表的名称。 |
| Tablename in field? | 启用此项可从输入字段中读取表名。 |
| Tablename field | 指定包含参数和参数类型的字段。 |
| Columnname field | 输入流中列字段的名称。 |
| Result fieldname | 结果布尔标志字段的名称。 |
