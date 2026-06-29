# MonetDB 批量加载器

MonetDB 批量加载器（MonetDB Bulk Loader）转换将数据批量加载到 MonetDB。这显著加快了向 MonetDB 加载数据的速度。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 目标 MonetDB 数据库连接 |
| 目标表 | 要加载的目标表名 |
| 字段映射 | 输入字段到表列的映射 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 批量加载显著快于逐行插入，适合大批量数据加载。
