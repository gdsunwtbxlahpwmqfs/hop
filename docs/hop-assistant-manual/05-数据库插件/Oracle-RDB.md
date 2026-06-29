# Oracle RDB（Oracle RDB）

## 概述

Oracle RDB 是 Oracle 公司提供的关系型数据库管理系统，运行在 OpenVMS 平台上。使用此数据库需要单独下载 JDBC 驱动程序。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://www.oracle.com/database/technologies/rdb-related-products-downloads.html |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://www.oracle.com/technetwork/database/database-technologies/rdb/documentation/rdbjdbc-ug-725-129654.pdf |
| JDBC 连接串 | jdbc:rdbThin://&lt;node&gt;:&lt;port&gt;/&lt;database_specification&gt; |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
