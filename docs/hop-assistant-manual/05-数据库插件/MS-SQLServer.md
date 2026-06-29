# MS SQL Server（MS SqlServer）

## 概述

此数据库类型使用 JTDS 驱动程序。在创建新的数据库连接时，建议使用原生（Native）连接类型。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://sourceforge.net/projects/jtds/files/jtds/ |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | http://jtds.sourceforge.net/faq.html |
| JDBC 连接串 | jdbc:jtds:sqlserver://&lt;server&gt;[:&lt;port&gt;][/&lt;database&gt;][;&lt;property&gt;=&lt;value&gt;[;...]] |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
