# Snowflake（Snowflake）

## 概述

Snowflake 是一个基于云的现代数据平台，提供数据仓库、数据湖和数据工程等功能。其 JDBC 驱动已内置在 Qi 数据治理平台 中。Snowflake 支持建立 SSL 认证连接。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 内置（Included） |
| 内置版本 | 4.2.0 |
| Hop 依赖 | 无 |
| 文档 | https://docs.snowflake.net/manuals/user-guide/jdbc-configure.html |
| JDBC 连接串 | `jdbc:snowflake://<account_name>.snowflakecomputing.com/?<connection_params>` |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
