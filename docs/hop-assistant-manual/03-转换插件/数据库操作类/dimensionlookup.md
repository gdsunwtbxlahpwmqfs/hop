# ![Dimension lookup/update transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/dimensionlookup.svg) Dimension lookup/update

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 公共字段

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Update the dimension? | 启用此项可根据输入流中的信息更新维度；如果未启用，维度仅执行查找并将技术键字段添加到流中。 |
| Connection | 维度表所在的数据库连接名称。 |
| Target schema | 用于指定 schema 名称。 |
| Target table | 维度表的名称。 |
| Commit size | 定义提交大小，例如将提交大小设置为 10 将每 10 次插入或更新生成一次提交。 |
| Caching a |  |
| Get Fields button | 填入输入流中所有可用字段，不包括您指定的键。 |
| SQL button | 生成用于构建维度的 SQL，并允许您执行此 SQL。 |

### Keys 标签页
指定数据流和维度表中键的名称。
这将使 transform 能够执行查找。

| 选项 | 描述 |
|---|---|
| Dimension field | 源系统中使用的键字段。例如：客户编号、产品 ID 等。 |
| Stream field | 包含从源系统键字段获取的值的流字段。 |

### Fields 标签页
对于维度中必须包含的每个字段，您可以指定是要更新值（适用于所有版本，这是 Type I 操作），还是希望将值作为新版本插入到维度中。
在我们截图中使用的示例中，出生日期是不随时间变化的，因此如果出生日期发生变化，意味着之前版本中的数据是错误的。
那么，在维度条目的所有版本中更正先前的值就是合乎逻辑的。

| 选项 | 描述 |
|---|---|
| Dimension field | 包含维度实际信息的字段。 |
| Stream field to compare with | 包含要分配给该表字段的传入值的流字段。 |
| Type of dimension update | 应用的更新类型（详见本文档下方的"更新"部分）。 |

### Technical key 标签页
此标签页包含与维度记录代理键创建相关的详细信息。

| 选项 | 描述 |
|---|---|
| Technical key field | 维度的主键；也称为代理键 (Surrogate Key)。 |
| Creation of technical key a | 指示技术键的生成方式，您的连接类型不可用的选项将被灰显： |
| Do not check or insert the 'unknown' row |  |
| 默认情况下，Hop 会检查 ID 0 或 1 处是否有 'unknown' 记录。 |  |

### Versioning 标签页
此标签页定义记录版本的生成方式。

| 选项 | 描述 |
|---|---|
| Version field | 存储版本号（修订号）的字段名称。 |
| Stream Datefield | 如果您有维度条目最后更改的日期，可以在此指定该字段的名称。 |
| Date range start field | 指定维度条目起始范围的名称。 |
| Use an alternative start date? a | 启用后，您可以选择"Min."的替代项。 |
| Table date range end | 维度条目结束范围的名称。 |

## 一般注意事项
作为此 transform 类型查找或更新操作的结果，一个包含维度技术键的字段会被添加到流中。
如果未找到字段，则返回未找到维度条目的值（0 或 1，取决于数据库类型）。

许多可选字段（在"Fields"标签页中）由 transform 自动管理。
您可以在"Dimension Field"列中指定表字段名称。
以下是可选字段：

- 最后插入或更新日期（无流字段作为来源）：添加并管理一个日期字段
- 最后插入日期（无流字段作为来源）：添加并管理一个日期字段
- 最后更新日期（无流字段作为来源）：添加并管理一个日期字段
- 最后版本（无流字段作为来源）：添加并管理一个布尔字段。（根据您的数据库连接设置和此类数据类型的可用性，转换为 Char(1) 或布尔数据库数据类型）
- 这作为最后版本的当前有效维度条目指示器：因此当 Type II 属性发生变化并创建新版本时（用于跟踪历史），先前版本中的'Last version'属性设置为'False/N'，而具有最新版本的新记录设置为'True/Y'。

## 功能

正如 transform 名称所暗示的，transform 的功能分为两类：查找和更新。

### 查找

在只读模式下（禁用更新选项），transform 仅在缓慢变化维度中执行查找。
transform 将在指定的数据库连接和指定的 schema 中查找维度表。
查找不仅使用指定的自然键（使用"等于"条件），还使用指定的"Stream datefield"（见下文）。
应用的条件是：

====
"Table date range start" <= "Stream datefield" AND "Table date range end" > "Stream datefield"

====

当未指定"Stream datefield"时，我们使用当前系统日期来查找正确的维度版本记录。

当未找到行时，返回"unknown"键。
（"unknown"键将是 0 或 1，取决于您是否为技术键字段选择了自增字段）。
请注意，我们不区分"Unknown"、"Not found"、"Empty"、"Illegal format"等。
但这些细微差别可以手动添加。
没有什么能阻止您在数据到达此 transform 之前使用 Filter、正则表达式等将这些类型清除。
我们建议您为这些特殊维度条目情况手动添加值 -1、-2、-3 等，就像在填充维度表之前添加"Unknown"行的具体细节一样。

- 不要为自然键使用 NULL 值。
NULL 值无法比较，且大多数数据库不会对其进行索引。
即使我们支持键中的 null 值（这在很大程度上也没有意义），也很可能导致严重的查找性能问题。
- 请注意输入流中的数据类型与自然键中的数据类型不同时可能出现的数据转换问题。
例如，如果 transform 输入中有字符串而数据库中使用的是整数，请确保您能够将字符串转换为数字。
将此视为最佳实践，在此 transform 之前执行此操作以确保按计划工作。
另一个典型问题是浮点数比较。
请避免使用这些类型。
我们建议您使用合理的数据类型，如 Integer 或长整数。
避免使用 Double、Decimal 或 Oracle 的 Number 等通用数据类型（没有长度或精度；它隐式使用精度 38，导致我们使用较慢的 BigNumber 数据类型）。

### 更新

在更新模式下（启用更新选项），transform 首先如上述"查找"部分所述执行维度条目的查找。
但查找的结果不同。
不仅从查询中检索技术键，还检索维度属性字段。
然后进行逐字段比较。
结果可能是以下情况之一：

- 未找到记录，我们在表中插入新记录。
- 找到记录且以下任一情况为真：
** 一个或多个属性不同且具有"Insert"（Kimball Type II）设置：插入新的维度记录版本。
** 一个或多个属性不同且具有"Punch through"（Kimball Type I）设置：更新所有维度记录版本中的这些属性。
** 一个或多个属性不同且具有"Update"设置：更新最后一个维度记录版本中的这些属性。
** 所有属性（字段）完全相同：不执行更新或插入。
- 新行的插入按以下方式进行：
** 当前行的"date_to"更新为"Stream date field"
** 插入新行，其中属性变化按上一段规则记录。"date_from"字段更新为"Stream date field"，"date_to"更新为表范围结束日期的最大日期。
** 新行的版本号递增 1。
** Stream date field"不能早于当前有效行的最早开始日期。
** select min(date_from) from dim_table where date_to = "2199-12-31 23:59:59.999"
** 确保传入行按"Stream date field"排序非常重要。
