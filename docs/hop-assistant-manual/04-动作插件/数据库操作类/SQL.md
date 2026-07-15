# SQL

## 描述

`SQL` action 从此 action 的配置或从文件执行一个或多个 SQL 脚本。

您可以执行多个 SQL 语句，只要它们以分号分隔即可。

SQL workflow action 非常灵活；您可以执行存储过程调用、创建和分析表等。

与 SQL workflow action 相关的常见用途包括截断表、删除索引、分区加载、刷新物化视图、禁用约束、禁用统计信息等。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Database Connection | 要使用的数据库连接。 |
| SQL from file | 启用此选项以从 SQL filename 指定的文件加载 SQL 语句 |
| SQL filename | 包含 SQL 语句的文件的文件名。 |
| File encoding | SQL 文件的字符编码（例如 UTF-8、windows-1252）。留空以使用系统默认值。当文件是 UTF-8 但系统使用其他编码（例如 Windows-1252）时很有用。 |
| Send SQL as single statement? | 启用此选项以不按分号分隔语句。 |
| Use variable substitution? | 允许在 SQL 脚本中使用变量。 |
| SQL script | 要执行的 SQL 脚本。 |
