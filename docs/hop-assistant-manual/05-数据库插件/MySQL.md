# MySql

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | [驱动链接](https://dev.mysql.com/downloads/connector/j/)（使用 Platform Independent） |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://dev.mysql.com/doc/connector-j/8.0/en/) |
| JDBC Url | jdbc:mysql://hostname:3306/databaseName |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |

**重要：** 为 mysql 创建连接时，请确保选择正确的"database type"。
Mysql 8+ 类型适用于当前的驱动。如果需要，您可以使用 Mysql 驱动来使用旧的"org.gjt.mm.mysql.Driver"驱动路径。
