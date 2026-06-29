# Oracle 批量加载器

Oracle 批量加载器（Oracle Bulk Loader）转换将数据从 Hop 流式传输到 Oracle。它将接收的数据写入适当的加载格式，然后调用 Oracle SQL*Loader 将数据传输到指定表。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 目标 Oracle 数据库连接 |
| 目标表 | 要加载的目标表名 |
| SQL*Loader 路径 | Oracle SQL*Loader 可执行文件路径 |
| 控制文件 | SQL*Loader 控制文件 |
| 数据文件 | 加载的数据文件 |
| 字段映射 | 输入字段到表列的映射 |

## 注意事项

- 该转换通过 Oracle SQL*Loader 实现批量加载。
- 需要系统上安装并配置 Oracle SQL*Loader。
- 性能优于通过表输出转换逐行插入。
