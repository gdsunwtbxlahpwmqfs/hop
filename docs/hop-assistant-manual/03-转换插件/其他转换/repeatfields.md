# ![Repeat Fields Icon, role="image-doc-icon"](../../assets/images/transforms/icons/repeatfields.svg) Repeat Fields

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 分组字段

在分组字段部分，您可以指定要在其中操作的分组。
每个新分组开始时没有前一行。

## 要重复的字段

| 选项 | 描述 |
|---|---|
| Repeat type |  |
| Source field | 要重复的源字段。 |
| Target field | 指定目标字段的名称（必填）。 |
| Indicator field name | 当使用 "Current when indicated" 类型时，这是包含指示值的字段名称。 |
| Indicator value | 当使用 "Current when indicated" 类型时，这是要匹配的指示值。 |

## Current when indicated

当我们处理来自多个来源的源数据时，例如关于客户的信息，
我们希望组装一个包含来自不同来源的所有不同字段的记录。
在我们的客户示例中，源系统 A 可能包含个人数据，如姓名、名字、出生日期等。
另一个源系统 B 可能包含客户的状态。
最后，源系统 C 包含一个指示财务状态的标志。
每个来源的数据在不同的时间点发生变化。在缓慢变化维度中（参见：[Dimension Lookup/Update](pipeline/transforms/dimensionlookup.md)），我们希望看到不同来源中发生的变化。
我们还希望在数据到达时处理来自各来源的数据。

| source | customer_id | timestamp | lastname | firstname | birthdate | status | indicator |
|---|---|---|---|---|---|---|---|
| A | 1 | 2025/01/01 12:00:00 | Mouse | Mickey | 1928/11/18 | null | null |
| B | 1 | 2025/01/01 13:00:00 | null | null | null | active | null |
| C | 1 | 2025/01/01 14:00:00 | null | null | null | null | positive |

我们想用此 transform 做的是，将源字段作为指示器，从源数据中提取适当的字段。我们希望最终得到以下结果：

| source | customer_id | timestamp | lastname | firstname | birthdate | status | indicator |
|---|---|---|---|---|---|---|---|
| A | 1 | 2025/01/01 12:00:00 | Mouse | Mickey | 1928/11/18 | null | null |
| B | 1 | 2025/01/01 13:00:00 | Mouse | Mickey | 1928/11/18 | active | null |
| C | 1 | 2025/01/01 14:00:00 | Mouse | Mickey | 1928/11/18 | active | positive |

然后可以使用 [Dimension Lookup/Update](pipeline/transforms/dimensionlookup.md) transform 将这 3 行数据创建为详细且正确的时间线。

> **💡 提示:** 添加目标缓慢变化维度中的最后一条记录以修改现有记录。

> **❗ 重要:** 如上所述但值得重复的是，确保您的数据按分组键（本示例中的客户 ID）排序，并且**同时**按时间戳排序，以获得正确的时间线结果。
