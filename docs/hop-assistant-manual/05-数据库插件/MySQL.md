# MySQL（MySQL）

## 概述

MySQL 是一个流行的开源关系型数据库管理系统。使用此数据库需要单独下载 JDBC 驱动程序。

**重要提示：** 为 MySQL 创建连接时，请确保选择正确的 "database type"（数据库类型）。MySQL 8+ 类型适用于当前的驱动程序。如有需要，您可以使用 MySQL 驱动程序来使用旧的 "org.gjt.mm.mysql.Driver" 驱动路径。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://dev.mysql.com/downloads/connector/j/（请使用 Platform Independent 版本） |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://dev.mysql.com/doc/connector-j/8.0/en/ |
| JDBC 连接串 | jdbc:mysql://hostname:3306/databaseName |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
