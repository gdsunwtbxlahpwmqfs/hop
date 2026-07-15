# ![Table Compare transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/tablecompare.svg) Table Compare

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项
### 参考/比较数据 选项卡

| 选项 | 描述 |
|---|---|
| Reference connection / Compare connection | 用于获取参考/比较表数据的数据库连接。 |
| Reference schema field / Compare schema field | 包含参考/比较表的 schema 名称。 |
| Reference table field / Compare table field | 包含实际的表名称。 |
| Reference CTE field / Compare CTE field | 指定用于表查询以比较值的通用表表达式（CTE）。 |

### 其他字段 选项卡

| 选项 | 描述 |
|---|---|
| Key fields field | 应包含一个逗号分隔的字段列表，这些字段构成您要比较的表的"主键"。 |
| Exclude fields field | 包含您想从比较中排除的列的逗号分隔列表。 |

### 附加字段 选项卡

| 选项 | 描述 |
|---|---|
| Number of errors field | 允许您指定输出列的名称，该列将包含表比较中发现的总错误数。 |
| Number of reference/compare table records field | 允许您指定字段的名称，该字段将包含每个表中找到的实际记录数。 |
| Number of left/inner/right join errors field | 允许您指定字段名称，该字段将包含每种连接类型发现的错误数。 |
| Error handling key description input field | 允许您指定输出字段的名称，用于出错记录的"where 子句"。 |
| Error handling reference/compare value input field | 允许您指定实际差异值的输出字段名称。 |

### 错误代码

| 错误代码 | 错误描述 |
|---|---|
| TAC001 | 参考表中的字段数与比较表中的字段数不同 |
| TAC002 | 在参考表中未找到键 |
| TAC003 | 在比较表中未找到键 |
| TAC004 | 在比较表中找到的记录在参考表中未找到 |
| TAC005 | 参考表中的记录在比较表中未找到对应键的记录 |
| TAC006 | 参考数据和比较数据之间发现字段差异。 |
| TAC007 | 比较表时未定义键字段 |
| TAC008 | 未为比较操作定义参考表或比较表，跳过输入行。 |
