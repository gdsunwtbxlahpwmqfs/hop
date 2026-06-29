# Infobright DB（Infobright DB）

## 概述

Infobright DB 是一个面向列式存储的分析型数据库，兼容 MySQL 协议。此插件使用 MySQL 驱动程序，并依赖 MySQL 数据库插件。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 使用 MySQL 驱动 |
| 内置版本 | 无 |
| Hop 依赖 | MySQL 数据库插件 |
| 文档 | https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference.html |
| JDBC 连接串 | jdbc:mysql://hostname:3306/databaseName |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
