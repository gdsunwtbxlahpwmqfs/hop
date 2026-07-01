# Redshift（Amazon Redshift）

## 概述

Amazon Redshift 是 AWS 提供的云数据仓库服务，适用于大规模数据分析场景。其 JDBC 驱动已内置在 hfxt data process 中，此插件依赖 PostgreSQL 数据库插件。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 内置（Included） |
| 内置版本 | 2.1.0.32 |
| Hop 依赖 | PostgreSQL 数据库插件 |
| 文档 | https://docs.aws.amazon.com/redshift/latest/mgmt/configure-jdbc-connection.html |
| JDBC 连接串 | jdbc:redshift://endpoint:port/database |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
