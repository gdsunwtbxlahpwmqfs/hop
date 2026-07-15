# Apache Hive

## 特性

| 特性 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | 已内置 |
| 内置版本 | 3.1.3 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients#HiveServer2Clients-JDBC) |
| JDBC Url | jdbc:hive2://<host:port>,<host2:port2>/databaseName |
| 驱动文件夹 | Hop Installation/plugins/databases/hive/lib |

## 分区

Apache Hive 需要在字段定义之前生成 PARTITION 子句。
为此，您可以在名为 'Table-partition clauses' 的特定 Hive 字段中添加表和子句定义。指定格式如下：

`TABLE1(CLAUSE1);SCHEMA2.TABLE2(CLAUSE2)`

例如，如果您想按 `statecode` 动态分区 `customers` 表，可以添加：`customers(statecode)`，对于 `INSERT INTO` 语句，Hop 会添加 `PARTITION(statecode)`。

提示：对于动态分区，您可以在 RDBMS metadata 的 `advanced` 选项卡中添加以下语句作为连接后立即执行的语句：

```sql
SET hive.exec.dynamic.partition = true;
SET hive.exec.dynamic.partition.mode = nonstrict;
```
