# Greenplum（Greenplum）

## 概述

Greenplum 是一个大规模并行处理（MPP）的无共享（shared-nothing）关系型数据库，专为大数据分析和数据仓库场景设计。此插件依赖 PostgreSQL 插件，使用此数据库需要单独下载 JDBC 驱动程序。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://docs.vmware.com/en/VMware-Greenplum/7/greenplum-database/datadirect-datadirect_jdbc.html |
| 内置版本 | 无 |
| Hop 依赖 | PostgreSQL 插件 |
| 文档 | https://gpdb.docs.pivotal.io/590/datadirect/datadirect_jdbc.html |
| JDBC 连接串 | jdbc:pivotal:greenplum://host:port;DatabaseName=&lt;name&gt; |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
