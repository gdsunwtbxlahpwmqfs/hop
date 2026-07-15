# ![Combination lookup/update transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/combinationlookup.svg) Combination lookup/update

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Connection | 维度表所在的数据库连接名称。 |
| Target schema | 用于指定 schema 名称。 |
| Target table | 维度表的名称。 |
| Commit size |  |
| 定义提交大小，例如设置为 10 将每 10 次插入或更新生成一次提交。 |  |
| Cache size in rows | 以行数为单位的缓存大小，保存在内存中通过减少数据库往返次数来加速查找。 |
| Key fields | 指定数据流和维度表中键的名称。 |
| Technical key field | 指示维度主键的字段。 |
| Creation of technical key a | 指定技术键的生成方式，您的连接不可用的选项将被灰显： |
| Remove lookup fields? | 如果想从输出中移除输入流中的所有查找字段，请启用此选项。 |
| Use hashcode | 此选项允许您生成一个哈希码，以数值形式（有符号 64 位整数）表示键字段中的所有值。 |
| Date of last update field | 需要时，指定源系统中要复制到数据仓库的最后更新日期字段（时间戳）。 |
| Get Fields button | 填入输入流中所有可用字段，不包括您指定的键。 |
| SQL button | 生成用于构建维度的 SQL，并允许您执行此 SQL。 |
