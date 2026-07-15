# ![Group By transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/groupby.svg) Group By

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Include all rows? | 如果您希望输出中包含所有行而不仅仅是聚合结果，请启用；为了区分输出中两种类型的行，需要在输出中添加一个标志。 |
| Temporary files directory | 存储临时文件的目录（当启用 Include all rows 选项且分组行数超过 5000 行时需要）；默认为系统的标准临时目录 |
| TMP-file prefix | 指定命名临时文件时使用的文件前缀 |
| Add line number, restart in each group | 启用以添加在每个组中从 1 重新开始的行号 |
| Line number field name | 添加的用于包含行号的字段名称。 |
| Always give back a row | 如果启用此选项，Group By transform 将始终返回一个结果行，即使没有输入行。 |
| Group fields table | 指定您要进行分组的字段。 |
| Aggregates table a | 指定必须聚合的字段、方法以及生成的新字段名称。 |
