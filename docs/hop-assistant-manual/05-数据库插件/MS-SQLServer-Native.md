# MS SQL Server 原生（MS SqlServer Native）

## 概述

此数据库类型使用 Microsoft 官方的原生 JDBC 驱动程序，支持集成身份验证（Integrated Authentication / Windows 身份验证）。

原生 Microsoft SQL JDBC 驱动程序附带额外文件，可使用您当前的 MS Windows 凭据进行身份验证。当您从 Microsoft 站点下载 JDBC 驱动程序并解压后，将看到如下目录结构，其中包含用于身份验证支持（auth）的本地库文件。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15 |
| 内置版本 | 13.2.1.jre11 |
| Hop 依赖 | 无 |
| 文档 | https://docs.microsoft.com/en-us/sql/connect/jdbc/setting-the-connection-properties?view=sql-server-ver15 |
| JDBC 连接串 | jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]] |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
