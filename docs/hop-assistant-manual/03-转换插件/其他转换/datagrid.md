# ![Data Grid transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/datagrid.svg) Data Grid

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Meta 选项卡 | 指定数据的字段元数据（输出规范） |
|---|---|
| Data 选项卡 | 包含数据。 |

## 元数据注入支持

该 Transform 支持元数据注入。
它由两部分组成：meta（字段定义）和 data。
meta 需要多行来定义字段。
data 部分包含 2 个注入字段，第一个是包含所有值的连接字符串，第二个是用于拆分该特定行的分隔符。
可以混用分隔符，当为空时使用 "," 作为拆分字符串。

## 限制

目前尚无法在两个现有列之间插入列，或总体上重新排列元数据选项卡中的字段顺序。

如果你计划这样做，请确保先将数据部分复制到电子表格中（选择所有行并复制/粘贴整个网格），修改数据后再将其复制回数据部分。
