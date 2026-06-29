# Vertica 批量加载器

Vertica 批量加载器（Vertica Bulk Loader）使用 [VerticaCopyStream](https://www.vertica.com/docs/12.0.x/HTML/Content/Authoring/ConnectingToVertica/ClientJDBC/UsingVerticaCopyStream.htm) 流式传输数据到 Vertica 数据库。

这通常比通过表输出转换加载数据快得多。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 目标 Vertica 数据库连接 |
| 目标表 | 要加载的目标表名 |
| 字段映射 | 输入字段到表列的映射 |

## 注意事项

- 该转换使用 VerticaCopyStream 流式加载，性能显著优于逐行插入。
- 适用于大批量数据加载到 Vertica 数据库。
