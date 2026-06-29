# Databricks（Databricks）

## 概述

Databricks 是一个基于 Apache Spark 的统一数据分析平台。请查阅 https://docs.databricks.com/aws/en/integrations/compute-details 的 Databricks 文档，了解如何获取连接详情以指定您的目录（catalog）。

**注意：** 在 Databricks 连接中不使用数据库名称（database name）字段，请使用目录名称（Catalog Name）字段。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://docs.databricks.com/aws/en/integrations/jdbc/download |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://docs.databricks.com/aws/en/integrations/jdbc/ |
| JDBC 连接串 | jdbc:databricks://&lt;server-hostname&gt;:443;httpPath=&lt;http-path&gt;[;&lt;setting1&gt;=&lt;value1&gt;;&lt;setting2&gt;=&lt;value2&gt;;&lt;settingN&gt;=&lt;valueN&gt;] |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
