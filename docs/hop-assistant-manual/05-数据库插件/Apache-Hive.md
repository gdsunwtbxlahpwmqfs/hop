# Apache Hive（Apache Hive）

## 概述

Apache Hive 是建立在 Hadoop 之上的数据仓库基础设施，用于对存储在各种文件系统中的大型数据集进行查询和分析。其 JDBC 驱动已内置在 Qi 数据治理平台 中。

Apache Hive 需要在字段定义之前生成 PARTITION 子句。为此，您可以在名为 "Table-partition clauses" 的特定 Hive 字段中添加表和子句定义。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 内置（Included） |
| 内置版本 | 3.1.3 |
| Hop 依赖 | 无 |
| 文档 | https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients |
| JDBC 连接串 | jdbc:hive2://&lt;host:port&gt;,&lt;host2:port2&gt;/databaseName |
| 驱动文件夹 | Hop 安装目录/plugins/databases/hive/lib |
