# ![MongoDB Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mongodb-output.svg) MongoDB Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

### General

Transform name：指定 Pipeline 中此 MongoDB Output Transform 的唯一名称。

### Output options 选项卡

Output options 选项卡提供了向 MongoDB collection 插入数据的控制选项。
如果指定的 collection 不存在，将在插入文档之前创建它。

在此选项卡的字段中输入以下信息：

| 选项 | 描述 |
|---|---|
| MongoDB Connection | 用于此 MongoDB Output Transform 的 [MongoDB connection](../../06-元数据类型/mongodb-connection.md)。 |
| Collection | 指定输出的目标 collection。 |
| Batch insert size | 指定批量插入操作的批次大小。 |
| Truncate collection | 选择以在插入新数据之前删除目标 collection 中的现有数据。 |
| Update | 为指定的数据库和 collection 设置更新写入方法。 |
| Upsert | 选择以将写入方法从 insert 更改为 upsert。 |
| Multi-update | 选择以更新每次 update 或 upsert 操作的所有匹配文档。 |
| Modifier update | 选择以启用 modifier（$ 操作符）来修改匹配文档中的单个字段。 |
| Number of retries for write operations | 指定写操作尝试的次数。 |
| Delay, in seconds, between retry attempts | 指定下次重试之前等待的秒数。 |

### Mongo document fields 选项卡

使用 Mongo document fields 选项卡定义进入 Transform 的字段值如何写入 Mongo 文档。
Modifier policy 列控制 modifier 操作的执行何时影响特定字段。
当一个 Mongo 文档的数据分散在多个传入 Hop 行中，或者无法同时执行影响同一字段的不同 modifier 操作时，可以使用 modifier policy。

有 2 个辅助按钮可以使用：
- *Get fields*：用传入字段的名称填充表格的 Name 列。
- *Preview document structure*：打开一个对话框，显示将以 JSON 格式写入 MongoDB 的结构。

在此选项卡的字段中输入以下信息：

| 列 | 字段描述 |
|---|---|
| Name | 传入字段的名称。 |
| Mongo document path | 以点号表示法格式表示的文档中字段的层次路径。 |
| Use field name | 是否使用传入字段名作为路径中的最终条目。 |
| NULL values | 指定是否在数据库中插入 null 值。 |
| JSON | 指示传入值是 JSON 文档。 |
| Match field for update | 指示在执行 upsert 操作时是否匹配字段。 |
| Modifier operation | 指定对现有文档字段的就地修改。 |
| Modifier policy | 控制 modifier 操作的执行何时影响字段。 |

#### 示例

以下是如何定义具有任意层次结构的文档结构的示例。
使用以下输入数据和文档字段定义在 MongoDB 中创建示例文档结构：

===== 输入数据

```
first, last, address, age
Bob, Jones ,"13 Bob Street", 34
Fred, Flintstone, "10 Rock Street",50
Zaphod, Beeblebrox, "Beetlejuice 1", 356
Noddy,Puppet,"Noddy Land",5
```
===== 文档字段定义

| Name | Mongo document path | Use field name | NULL values | JSON | Match field for update | Modifier operation | Modifier policy |
|---|---|---|---|---|---|---|---|
| first |  |  |  |  |  |  |  |
| top1 |  |  |  |  |  |  |  |
| Y |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N/A |  |  |  |  |  |  |  |
| Insert&Update |  |  |  |  |  |  |  |
| last |  |  |  |  |  |  |  |
| array[O] |  |  |  |  |  |  |  |
| Y |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N/A |  |  |  |  |  |  |  |
| Insert&Update |  |  |  |  |  |  |  |
| address |  |  |  |  |  |  |  |
| array[O] |  |  |  |  |  |  |  |
| Y |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N/A |  |  |  |  |  |  |  |
| Insert&Update |  |  |  |  |  |  |  |
| age |  |  |  |  |  |  |  |
| array[O] |  |  |  |  |  |  |  |
| Y |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N |  |  |  |  |  |  |  |
| N/A |  |  |  |  |  |  |  |
| Insert&Update |  |  |  |  |  |  |  |

====== 文档结构

{
  "top1" : {
    "first" : "<string val>"
   },
  "array" : [ { "last" : "<string val>" , "address" : "<string val>"}],
  "age" : "<integer val>"
}

### Create/drop indexes 选项卡

使用 Create/drop indexes 选项卡在一个或多个字段上创建和删除索引。
除非使用唯一索引，MongoDB 允许插入重复记录。
索引在 Transform 处理完所有行之后执行。

您可以使用 *Show indexes 按钮* 显示现有索引列表。

在此选项卡的字段中输入以下信息：

| 字段 | 描述 |
|---|---|
| Index fields | 指定单个索引（使用一个字段）或复合索引（使用多个字段）。 |
| Index opp | 指定是创建还是删除索引。 |
| Unique | 指定是否仅索引具有唯一值的字段。 |
| Sparse | 指定是否仅索引具有已索引字段的文档。 |

#### 创建/删除索引示例

以下选项定义了按升序排列的 "first" 和 "age" 字段的复合索引：

| Index fields | Index opp | Unique | Sparse |
|---|---|---|---|
| top1.first,age |  |  |  |
| Create |  |  |  |
| N |  |  |  |
| N |  |  |  |
