# ![Row Denormaliser transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/denormaliser.svg) Row Denormaliser

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Transform name | Transform 的名称。 |
|---|---|
| Key field | 定义输出行键的字段。 |
| Group fields | 在此指定构成分组的字段。 |
| Target fields | 通过为键字段指定 String 值来选择要反规范化的字段（见上文）。 |

## Metadata Injection 支持

您可以使用 ETL Metadata Injection transform 支持的字段，在运行时将 metadata 传递给您的 Pipeline。
所有字段都可以注入，用于聚合字段的值如下：

| key | value |
|---|---|
| TYPE_AGGR_NONE | 不执行聚合 |
| TYPE_AGGR_SUM | 对所有值求和 |
| TYPE_AGGR_AVERAGE | 计算平均值 |
| TYPE_AGGR_MIN | 取分组的最小值 |
| TYPE_AGGR_MAX | 取分组的最大值 |
| TYPE_AGGR_COUNT_ALL | 计算行数 |
| TYPE_AGGR_CONCAT_COMMA | 以逗号分隔聚合值 |

## 示例

### 输入数据
输入数据必须按分组键排序（本示例中的 **RecordID**），如有需要请使用 [Sort rows](pipeline/transforms/sort.md) transform：

| RecordID | key | value |
|---|---|---|
| 345-12-0000 | FirstName | Mitchel |
| 345-12-0000 | LastName | Runolfsdottir |
| 345-12-0000 | City | Jerryside |
| 976-67-7113 | FirstName | Elden |
| 976-67-7113 | LastName | Welch |
| 976-67-7113 | City | Lake Jamaal |
| 824-21-0000 | FirstName | Rory |
| 824-21-0000 | LastName | Ledner |
| 824-21-0000 | City | Scottieview |

### 反规范化数据
设置 **The key field** = "key" 并在 **The fields that make up the grouping** 中添加 **RecordID**。
按如下方式编译 **Target fields** 表：

| Target fieldname | Value fieldname | Key value | Type |
|---|---|---|---|
| FirstName | value | FirstName | String |
| LastName | value | LastName | String |
| City | value | City | String |
结果为：

| RecordID | FirstName | LastName | City |
|---|---|---|---|
| 345-12-0000 | Mitchel | Runolfsdottir | Jerryside |
| 976-67-7113 | Elden | Welch | Lake Jamaal |
| 824-21-0000 | Rory | Ledner | Scottieview |
