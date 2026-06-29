# SingleStore / MemSQL（SingleStore (MemSQL)）

## 概述

您可以在 https://www.singlestore.com/ 的 SingleStore 主页了解更多信息。SingleStore 在语法上是 MySQL 的一个变体，您可以将 MySQL 批量加载器（bulk loader）等操作与 SingleStore 配合使用。

有关 SingleStore JDBC 驱动程序的更多信息，请参见：https://docs.singlestore.com/cloud/developer-resources/connect-with-application-development-tools/connect-with-java-jdbc/the-singlestore-jdbc-driver/ 的 SingleStore JDBC 驱动文档。

**重要：** 您需要自行下载并安装 SingleStore JDBC 驱动程序。建议您将驱动程序 JAR 文件放在 `lib/jdbc` 文件夹中，或放在由 `HOP_SHARED_JDBC_FOLDERS` 变量指向的文件夹中。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 从 SingleStore 官网下载 |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://docs.singlestore.com/cloud/developer-resources/connect-with-application-development-tools/connect-with-java-jdbc/the-singlestore-jdbc-driver/ |
| JDBC 连接串 | jdbc:singlestore://&lt;database_computer&gt;[:&lt;port&gt;]/&lt;database_name&gt; |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
