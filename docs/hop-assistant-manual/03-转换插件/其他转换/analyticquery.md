# ![Analytic Query Icon, role="image-doc-icon"](../../assets/images/transforms/icons/analyticquery.svg) Analytic Query

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | 该 Transform 在 Pipeline 工作区中显示的名称。 |
| Group fields table | 指定你要分组的字段。 |
| Analytic Functions table | 指定要解析的分析函数。 |
| New Field Name | 你希望该新字段在数据流中命名的名称（例如 PREV_ORDER_DATE） |
| Subject | 要获取的已有字段（例如 ORDER_DATE） |
| Type |  |
| N | 要偏移的行数（向前或向后） |

## 分组字段示例

虽然指定分组不是必需的，但在某些情况下非常有用。
如果你创建了一个分组（由一个或多个字段组成），那么 "lead forward / lag backward" 操作仅在每个分组内执行。
例如，假设你有以下数据：

====
```bash
X   , Y
```----
aaa , 1
aaa , 2
aaa , 3
bbb , 4
bbb , 5
bbb , 6
```
====

你想创建一个名为 Z 的字段，其值为前一行的 Y 值。

如果你只关心 Y 字段，则不需要分组。
你将得到以下结果：

====
```bash
X   , Y , Z
```--------
aaa , 1 , <null>
aaa , 2 , 1
aaa , 3 , 2
bbb , 4 , 3
bbb , 5 , 4
bbb , 6 , 5
```
====

但如果你不想混淆 aaa 和 bbb 的值，可以按 X 字段分组，你将得到：

====
```bash
X   , Y , Z
```--------
aaa , 1 , <null>
aaa , 2 , 1
aaa , 3 , 2
bbb , 4 , <null>
bbb , 5 , 4
bbb , 6 , 5
```
====

因此，通过分组（前提是输入已按照你的分组方式排序），你可以确保 lead 或 lag 操作不会返回已定义分组之外的行值。
