# Teradata 批量加载器

Teradata 批量加载器（Teradata Bulk Loader / TeraFast）转换使用 fastload 命令行工具支持向 Teradata 数据库快速加载数据。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 目标 Teradata 数据库连接 |
| 目标表 | 要加载的目标表名 |
| fastload 路径 | Teradata fastload 工具路径 |
| 字段映射 | 输入字段到表列的映射 |

## 注意事项

- 该转换通过 Teradata fastload 命令行工具实现批量加载。
- 需要系统上安装并配置 Teradata fastload 工具。
- 性能优于逐行插入，适合大批量数据加载。
