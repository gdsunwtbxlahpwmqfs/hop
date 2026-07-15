# ![MongoDB Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mongodb-input.svg) MongoDB Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

### General

Transform name ：指定 Pipeline 中 MongoDB Input transform 的唯一名称。
Preview button：显示此 Transform 生成的行。
输入要预览的最大记录数，然后点击 OK。
预览数据显示在 Examine preview data 窗口中。

### Input options 选项卡

Input options 选项卡允许您指定要从中检索信息的连接和集合。

在 Input options 字段中输入以下信息：

| Option | Definition |
|---|---|
| MongoDB connection |  |
| 用于此 MongoDB Input transform 的 [MongoDB connection](../../06-元数据类型/mongodb-connection.md)。 |  |
| Collection |  |
| 要从中检索数据的集合名称。 |  |

### Query 选项卡

Query 选项卡允许您优化读取请求。
此选项卡在两种不同的查询模式下运行：

- *Query expression* 模式（默认）
- *Aggregation pipeline specification* 模式。

*Query is aggregation pipeline* 选项用于在两种模式之间切换。
Query expression 使用 MongoDB 的类 JSON 查询语言和查询运算符来执行[查询操作](https://docs.mongodb.com/manual/reference/operator/query/)。
*Aggregation pipeline specification* 字段使用 MongoDB 的[聚合框架](http://docs.mongodb.org/manual/applications/aggregation/)来转换和组合集合中的文档。
聚合管道将多个[pipeline 表达式](https://docs.mongodb.com/manual/core/aggregation-pipeline/#pipeline-expressions)连接在一起，前一个表达式的输出成为下一个的输入。

在 Query 字段中输入以下信息：

| Fields/Option | Definition |
|---|---|
| Query expression (JSON) |  |
| 在此字段中输入查询表达式以限制输出。 |  |
| Aggregation pipeline specification (JSON) |  |
| 选择 *Query is aggregation pipeline* 选项以显示 *Aggregation pipeline specification (JSON)* 字段。 |  |
| Query is aggregation pipeline |  |
| 选择此选项以使用聚合管道框架。 |  |
| Execute for each row |  |
| 选择此选项以对每行数据执行查询。 |  |
| Fields expression (JSON) |  |
| 输入参数以控制查询的投影（要返回的字段）。 |  |

### Fields 选项卡

使用 Fields 选项卡定义导出字段的属性。
Fields 选项卡在两种不同模式下运行：

1. 将所有字段包含在单个 JSON 字段中
2. 在输出中包含选定字段。

如果将输出存储在单个 JSON 字段中，可以使用 JSON Input transform 或使用 User Defined Java Class transform 来解析此 JSON。

*注意：* 选择 Output single JSON field 时，Fields 选项卡中除 Name of JSON output field 外的所有字段都不可用。
未选择 Output single JSON field 时，Name of JSON output field 不可用。

通用选项：

- *The Get fields button*：点击生成一组示例文档。
您可以在示例中编辑每个字段的字段名、路径和数据类型。
- *Output single JSON field*：指定查询结果为具有 String 数据类型的单个 JSON 字段（默认）。
- *Name of JSON output field*：指定包含服务器 JSON 输出的字段名称。

如果要输出不同字段，请在表格中输入以下信息：

| Option | Definition |
|---|---|
| # |  |
| 此条目在列表中的顺序。 |  |
| Name |  |
| 基于 Path 字段中值的字段名称。 |  |
| Path |  |
| Type |  |
| 指示数据类型。 |  |
| Indexed values |  |
| 为 String 字段指定逗号分隔的合法值列表。 |  |
| Sample: array min: max index |  |
| 指示示例文档中索引的最小值和最大值。 |  |
| Sample: #occur/#docs |  |
| 指示字段出现的频率和处理的文档数量。 |  |
| Sample: disparate types |  |
| 指示示例文档中是否有不同的数据类型填充同一字段。 |  |

## 示例

以下部分包含查询表达式和聚合管道的示例。

### 查询表达式

MongoDB 允许您使用特定字段和值在集合中选择和筛选文档。
[MongoDB Extended JSON](http://docs.mongodb.org/manual/reference/mongodb-extended-json/) 文档详细介绍了如何使用查询。
Qi Hop 仅支持本页讨论的功能。

下表显示了一些查询语法和结构的示例，您可以使用这些查询从 MongoDB 请求的数据：

| Query expression | Description |
|---|---|
| ```{ name : "MongoDB" }``` |  |
| 查询 name 字段值等于 MongoDB 的所有值。 |  |
| ```{ name : { '$regex' : "m.*", '$options' : "i" } }``` |  |
| 使用正则表达式查找以 m 开头的 name 字段，不区分大小写。 |  |
| ```{ name : { '$gt' : "M" } }``` |  |
| 搜索所有大于 M 的字符串。 |  |
| ```{ name : { '$lte' : "T" } }``` |  |
| 搜索所有小于或等于 T 的字符串。 |  |
| ```{ name : { '$in' : [ "MongoDB", "MySQL" ] } }``` |  |
| 查找所有名称为 MongoDB 或 MySQL 的记录（参考）。 |  |
| ```{ name : { '$nin' : [ "MongoDB", "MySQL" ] } }``` |  |
| 查找所有名称既不是 MongoDB 也不是 MySQL，或该字段未设置的记录。 |  |
| ```{ created_at : { $gte : { $date : "2014-12-31T00:00:00.000Z" } } }``` |  |
| 查找所有大于或等于指定 UTC 日期的 created_at 文档。 |  |
| ```{ $where : "this.count == 1" }``` |  |
| 使用 JavaScript 评估条件。 |  |
| ```{ $query: {}, $orderby: { age : -1 } }``` |  |
| 返回集合中所有文档，按 age 字段降序排列。 |  |

### 聚合管道

MongoDB 允许您使用[聚合](http://docs.mongodb.org/manual/tutorial/aggregation-examples/)管道框架来选择和筛选文档。
MongoDB 文档中的 Aggregation 页面提供了更多函数调用示例。

下表显示了一些查询语法和结构的示例，您可以使用这些查询从 MongoDB 请求的数据：

| Query expression | Description |
|---|---|
| ```{ $match : {state : "FL", city : "ORLANDO" } }, {$sort : {pop : -1 } }``` |  |
| 返回 state 字段值为 FL 且 city 字段值为 ORLANDO 的所有文档的所有字段。 |  |
| ```{ $group : { _id: "$state"} }, { $sort : { _id : 1 } }``` |  |
| 返回一个名为 _id 的字段，包含 state 的去重值，按升序排列。 |  |
| ```{ $match : {state : "FL" } }, { $group: {_id: "$city" , pop: { $sum: "$pop" } } }, { $sort: { pop: -1 } }, { $project: {_id : 0, city : "$_id" } }``` |  |
| 返回 state 字段值为 FL 的所有文档，按城市聚合所有 pop 值，按人口降序排列，并返回一个名为 city 的字段。 |  |
| ```{ $unwind : "$result" }``` |  |
| 将数组的元素逐一拆分，为数组中的每个元素返回一个文档。 |  |
