# PostgreSQL 批量加载器

PostgreSQL 批量加载器（PostgreSQL Bulk Loader）转换使用 [COPY DATA FROM STDIN](https://www.postgresql.org/docs/current/sql-copy.html) 将数据从 Hop 流式传输到 PostgreSQL 数据库。

> **提示**：布尔流字段序列化为 `t` 或 `f`，PostgreSQL `COPY` 接受这些值用于布尔列。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 目标 PostgreSQL 数据库连接 |
| 目标表 | 要加载的目标表名 |
| 加载操作 | 加载操作类型（插入、截断等） |
| 字段映射 | 输入字段到表列的映射 |
| 分隔符 | COPY 数据的分隔符 |
| 封闭符 | 字段的封闭符 |

## 注意事项

- 该转换使用 `COPY DATA FROM STDIN` 流式加载数据，性能远优于逐行插入。
- 布尔字段以 `t`/`f` 格式序列化。
