# Apache Doris（Apache Doris）

## 概述

Apache Doris 与 MySQL 兼容，因此您可以使用 MySQL 或 MariaDB 的 JDBC 驱动程序。请访问 https://doris.apache.org 的 Apache Doris 官方网站获取更多信息。

JDBC 连接串的端口与 Doris 中 Frontend 的查询端口相同。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://dev.mysql.com/downloads/connector/j/（请使用 Platform Independent 版本） |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://dev.mysql.com/doc/connector-j/8.0/en/ |
| JDBC 连接串 | jdbc:mysql://hostname:9030/databaseName |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
