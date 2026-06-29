# InterSystems Cache（InterSystems Cache）

## 概述

InterSystems Cache 是 InterSystems 公司提供的高性能多维数据库，支持 SQL 和对象访问。使用此数据库需要单独下载 JDBC 驱动程序。

**注意：** 这是旧的驱动连接方式，建议优先使用新的 InterSystems Iris 连接。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://docs.intersystems.com/latest/csp/docbook/DocBook.UI.Page.cls?KEY=BGJD_intro |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://cedocs.intersystems.com/latest/csp/docbook/DocBook.UI.Page.cls?KEY=BGJD |
| JDBC 连接串 | jdbc:Cache://hostname:1972/database |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
