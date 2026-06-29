# Microsoft Access（Microsoft Access）

## 概述

创建新的数据库连接时，请在数据库名称（database name）字段中指定 .mdb 或 .accdb 文件的数据库路径。

此数据库连接使用 UCanAccess 驱动程序，该驱动程序在读取操作（如 TableInput、DatabaseLookup）方面表现良好，但不适合写入操作。对于写入操作，建议使用 Microsoft Access Output（Microsoft Access 输出）转换。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 内置（Included） |
| 内置版本 | 5.1.5 |
| Hop 依赖 | 无 |
| 文档 | https://spannm.github.io/ucanaccess/10-ucanaccess.html |
| JDBC 连接串 | jdbc:ucanaccess://path.mdb |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
