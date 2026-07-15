# ![Execute SQL script transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/sql.svg) Execute SQL script

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称；该名称在单个 Pipeline 中必须唯一 |
| Connection | 选择要使用的数据库连接 |
| SQL script to execute | 指定要执行的 SQL。 |
| Execute for each row? | 选择此选项以为每个传入行执行 SQL。 |
| Execute as a single statement | 此选项不会按 ; 拆分语句，而是将整个 SQL 发送到数据库。 |
| Variable substitution | 如果你想在 SQL 中使用变量，例如 {openvar}table_name{closevar}，需要勾选此选项。如果你使用 Parameter 字段，它们仅与 SQL 中的"问号"配合使用（见下文）。 |
| Bind parameters? | 勾选此选项以使用预编译语句绑定参数，否则此 Transform 将对参数执行字面字符串替换。 |
| Quote Strings? | 此选项根据数据库方言在字符串周围添加引号，并转义 CR、LF 和引号字符本身等特殊字符。 |
| Parameters | 将按给定顺序替换查询中问号的参数列表。 |
| Field to contain insert stats | 可选：如果你想在我们的流中获取一个包含已插入记录数的附加字段，请在此定义字段名。 |
| Field to contain update stats | 与插入统计相同，但用于更新的行。 |
| Field to contain delete stats | 与插入统计相同，但用于删除的行。 |
| Field to contain read stats | 与插入统计相同，但用于读取的行。 |
