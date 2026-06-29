# Google BigQuery（Google BigQuery）

## 概述

Google BigQuery 是 Google Cloud 提供的企业级数据仓库服务，支持快速进行 SQL 查询分析。使用此数据库需要单独下载 Simba JDBC 驱动程序。

Simba 驱动以 .zip 压缩包形式打包，包含多个 jar 文件。使用 BigQuery JDBC 与 Apache Hop 时，只需要驱动中的一部分 jar 文件。此外，某些 jar 文件可能与 Hop 自带的 jar 冲突，*必须*排除在外。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://cloud.google.com/bigquery/docs/reference/odbc-jdbc-drivers |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://www.simba.com/products/BigQuery/doc/JDBC_InstallGuide/content/jdbc/d-intro.htm |
| JDBC 连接串 | jdbc:bigquery://[Host]:[Port];ProjectId=[Project];OAuthType=[AuthValue] |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
