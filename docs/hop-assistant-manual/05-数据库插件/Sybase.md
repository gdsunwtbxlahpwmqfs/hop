# Sybase（SAP Sybase ASE）

## 概述

Sybase（现为 SAP Sybase Adaptive Server Enterprise，简称 ASE）是一个关系型数据库管理系统。其 JDBC 驱动包含在数据库安装包中。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 包含在数据库安装中 |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | http://jtds.sourceforge.net/faq.html |
| JDBC 连接串 | jdbc:jtds:sybase://&lt;server&gt;[:&lt;port&gt;][/&lt;database&gt;][;&lt;property&gt;=&lt;value&gt;[;...]] |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
