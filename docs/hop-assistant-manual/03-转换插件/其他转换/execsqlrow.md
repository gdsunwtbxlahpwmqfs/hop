# ![Execute row SQL script transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/execsqlrow.svg) Execute row SQL script

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |  |
|---|---|---|
| Transform name | Transform 的名称；该名称在单个 Pipeline 中必须唯一 |  |
| Connection | 选择要使用的数据库连接 |  |
| Commit | 执行数据库提交前发送的行数 |  |
| SQL field name | 包含要执行 SQL 或可选地指定包含 SQL 的文件路径的字段 | Read SQL from file |
| 如果勾选，则 SQL field name 选项指定一个包含 SQL 的文件，否则 SQL field name 选项指定要执行的实际 SQL。 |  |  |
| Field to contain insert stats | 可选：如果你想在我们的流中获取一个包含已插入记录数的附加字段，请在此定义字段名。 |  |
| Field to contain update stats | 与插入统计相同，但用于更新的行。 |  |
| Field to contain delete stats | 与插入统计相同，但用于删除的行。 |  |
| Field to contain read stats | 与插入统计相同，但用于读取的行。 |  |

## 说明

由于该 Transform 的脚本/动态特性，它不使用预编译语句，因此不适合快速或最优的性能。
如需良好的性能，请使用专用 Transform，如 Table Output（insert into）、Table Input（Select）、Update、Delete 等。
