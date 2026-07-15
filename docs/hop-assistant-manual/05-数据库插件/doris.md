# Apache Doris

Doris 兼容 MySQL，因此您可以使用 MySQL 或 MariaDB JDBC 驱动。
请访问 [Apache Doris 网站](https://doris.apache.org)获取更多信息。

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | [驱动链接](https://dev.mysql.com/downloads/connector/j/)（使用 Platform Independent） |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://dev.mysql.com/doc/connector-j/8.0/en/) |
| JDBC Url | jdbc:mysql://hostname:9030/databaseName |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |

JDBC Url 的端口与 Doris 中 Frontend 的查询端口相同。
