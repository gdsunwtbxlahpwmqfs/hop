# ![MongoDB Delete transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mongodb-delete.svg) MongoDB Delete

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

### General

Transform name：指定 Pipeline 中此 MongoDB Delete Transform 的唯一名称。

### Delete options 选项卡

| 字段 | 描述 |
|---|---|
| MongoDB connection | 用于 MongoDB Delete Transform 的 [MongoDB connection](metadata-types/mongodb-connection.md)。 |
| Collection | 要从中删除数据的 collection 名称。 |
| Number of retries for write operations | 写操作的重试次数 |
| Delay (in seconds) between retry attempts | 重试之间的延迟（秒） |

### Delete query

- 使用 JSON 查询：
** 当禁用时（默认），Transform 基于 key/value 对比较删除文档。表格需要以下字段：
*** Mongo document path：要删除的文档路径
*** Comparator：`=`、`<>`、``、``、`<`、`<=`、`>`、`>=`、`BETWEEN`、`IS NULL`、`IS NOT NULL`
*** Incoming field 1：第一个比较流字段
*** Incoming fields 2：第二个比较流字段（用于 `BETWEEN`）
** 当启用时：
*** 将删除查询传递给 MongoDB 执行删除。
*** execute for each row：为每个传入行将删除查询传递给数据库

### Delete query 示例：

基于文档路径和流字段删除（禁用 `use JSON query`）：
| Mongo document path | Comparator | Incoming field 1 | Incoming field 2 |
|---|---|---|---|
| name | = | lastname |  |
| firstname | = | firstname |  |

基于 JSON 查询删除（启用 `use JSON query`）：

`{$or: [{"name": "${NAME1}"},{"name": "${NAME2}"}, {"name": "${NAME3}"} ]}`
