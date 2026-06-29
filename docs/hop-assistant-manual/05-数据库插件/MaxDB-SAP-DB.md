# MaxDB / SAP DB（MaxDB (SAP DB)）

## 概述

MaxDB（原名 SAP DB）是 SAP 提供的关系型数据库管理系统，专为 SAP 应用环境优化。其 JDBC 驱动包含在数据库安装包中。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 包含在数据库安装中 |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://help.sap.com/saphelp_tm93/helpdata/en/37/5f6b6e966242aead8025bdc5296489/frameset.htm |
| JDBC 连接串 | jdbc:sapdb://&lt;database_computer&gt;[:&lt;port&gt;]/&lt;database_name&gt; |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
