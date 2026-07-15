# Oracle

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | [驱动链接](https://www.oracle.com/database/technologies/appdev/jdbc-downloads) |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://docs.oracle.com/cd/E11882_01/java.112/e16548/toc.htm) |
| JDBC Url | jdbc:oracle:thin:@hostname:port Number:databaseName |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |

> **💡 提示:** 从 Oracle Database 11g Release 1 (11.1) 开始，数据类型 `Date` 将默认映射为 `Timestamp`。
设置 JDBC 属性 `oracle.jdbc.mapDateToTimestamp=false` 以避免数据类型 `Date` 被转换为数据类型 `Timestamp`。
请查看关系型数据库连接文档中的 [Options](database/databases.md#_options.md) 获取更多信息。

## 创建连接

在 Qi Hop 中有 4 种方式可以创建到 Oracle 数据库的连接：

- 如果您有 SID，请使用此（旧）格式：`jdbc:oracle:thin:@hostname:PORT:SID`。
在数据库名称中加上 `:` 前缀填入 SID

- 如果您有服务名，请使用此（较新）格式：`jdbc:oracle:thin:@//HOSTNAME:PORT/SERVICENAME`。
在数据库名称中加上 `/` 前缀填入服务名

- 如果您想使用 TNS 格式：`jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=host) (PORT=port)) (CONNECT_DATA=(SERVICE_NAME=service_name))`。
在数据库名称中填入 TNS 描述，并将主机名和端口留空。

- 如果您想使用特定的 TNS_ADMIN，可以通过 TNS_ADMIN 属性提供 tnsnames.ora 的路径：`jdbc:oracle:thin:@mydb.mydomain?TNS_ADMIN=C:\\Temp\\tns`
使用手动连接 URL（而非 Options 选项卡，因为 Qi Hop 不会在 Oracle 的 URL 中传递选项）。
