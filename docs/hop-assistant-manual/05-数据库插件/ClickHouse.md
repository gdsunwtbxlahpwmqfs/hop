# ClickHouse（ClickHouse）

## 概述

ClickHouse 是一个面向列式存储（Columnar）的数据库管理系统，适用于在线分析处理（OLAP）场景。其 JDBC 驱动已内置在 Apache Hop 中。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 列式存储（Columnar） |
| 驱动 | 内置（Included） |
| 内置版本 | 0.9.0 |
| Hop 依赖 | 无 |
| 文档 | https://github.com/blynkkk/clickhouse4j |
| JDBC 连接串 | jdbc:clickhouse://&lt;host&gt;:&lt;port&gt;[/&lt;database&gt;] |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
