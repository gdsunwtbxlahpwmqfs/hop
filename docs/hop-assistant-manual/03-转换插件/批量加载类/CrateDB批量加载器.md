# CrateDB 批量加载器

CrateDB 批量加载器（CrateDB Bulk Loader）转换使用两种不同方式将数据从 Apache Hop 加载到 CrateDB：

- [COPY FROM](https://cratedb.com/docs/crate/reference/en/5.7/sql/statements/copy-from.html#copy-from) 命令；
- 用于批量操作的 [CrateDB HTTP 端点](https://cratedb.com/docs/crate/reference/en/latest/interfaces/http.html#bulk-operations)。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 目标 CrateDB 数据库连接 |
| 目标表 | 要加载的目标表名 |
| 加载方式 | 使用 COPY FROM 命令或 HTTP 端点 |
| 字段映射 | 输入字段到表列的映射 |

## 注意事项

- 支持通过 COPY FROM 命令或 HTTP 端点两种加载方式。
- HTTP 端点方式适用于不支持 COPY FROM 的环境。
