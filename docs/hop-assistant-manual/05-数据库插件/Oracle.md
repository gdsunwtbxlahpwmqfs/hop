# Oracle（Oracle Database）

## 概述

Oracle Database 是 Oracle 公司提供的关系型数据库管理系统，广泛应用于企业级应用。使用此数据库需要单独下载 JDBC 驱动程序。

**提示：** 从 Oracle Database 11g Release 1（11.1）开始，数据类型 `Date` 默认将映射为 `Timestamp`。设置 JDBC 属性 `oracle.jdbc.mapDateToTimestamp=false` 可以避免数据类型 `Date` 被转换为数据类型 `Timestamp`。请查阅关系型数据库连接文档中的选项（Options）部分获取更多信息。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://docs.oracle.com/cd/E11882_01/java.112/e16548/toc.htm |
| JDBC 连接串 | jdbc:oracle:thin:@hostname:port Number:databaseName |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
