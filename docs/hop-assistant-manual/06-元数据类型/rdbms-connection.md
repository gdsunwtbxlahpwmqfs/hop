# 关系型数据库连接

## 描述

![](../assets/images/icons/database.svg)

描述连接到关系型数据库所需的所有信息。

Hop 开箱即支持数十种关系型数据库。如果你的特定数据库没有内置支持，你可能仍然可以使用 Generic 数据库连接进行连接。

查看 [数据库](../05-数据库插件/databases.md)列表了解更多详情。

## 相关 plugin

Action：

- [Check DB Connections](../04-动作插件/其他动作/checkdbconnection.md)
- [Columns exist in table](../04-动作插件/其他动作/columnsexist.md)
- [MS SQL Bulk Loader](../04-动作插件/其他动作/mssqlbulkload.md)
- [MySQL Bulk File](../04-动作插件/其他动作/mysqlbulkfile.md)
- [MySQL Bulk Loader](../04-动作插件/其他动作/mysqlbulkload.md)
- [SQL](../04-动作插件/数据库操作类/sql.md)
- [Table Exists](../03-转换插件/数据库操作类/tableexists.md)
- [Truncate Tables](../04-动作插件/其他动作/truncatetables.md)
- [Wait for SQL](../04-动作插件/其他动作/waitforsql.md)

Transform：

- [Call DB Procedure](../03-转换插件/其他转换/calldbproc.md)
- [Column Exists](../03-转换插件/数据库操作类/columnexists.md)
- [Combination Lookup/Update](../03-转换插件/数据库操作类/combinationlookup.md)
- [Database Join](../03-转换插件/数据库操作类/databasejoin.md)
- [Database Lookup](../03-转换插件/数据库操作类/databaselookup.md)
- [Dynamic SQL Row](../03-转换插件/其他转换/dynamicsqlrow.md)
- [Execute SQL Row](../03-转换插件/其他转换/execsqlrow.md)
- [Insert/Update](../03-转换插件/其他转换/insertupdate.md)
- [Oracle Bulk Loader](../03-转换插件/其他转换/orabulkloader.md)
- [PostgreSQL Bulk Loader](../03-转换插件/其他转换/postgresbulkloader.md)
- [Synchronize After Merge](../03-转换插件/其他转换/synchronizeaftermerge.md)
- [Table Compare](../03-转换插件/其他转换/tablecompare.md)
- [Table Exists](../03-转换插件/数据库操作类/tableexists.md)
- [Table Input](../03-转换插件/输入类/tableinput.md)
- [Table Output](../03-转换插件/输出类/tableoutput.md)
- [Teradata Bulk Loader](../03-转换插件/其他转换/terafast.md)
- [Update](../03-转换插件/数据库操作类/update.md)

## 选项

下面描述的选项适用于 Generic 数据库连接。

你所选数据库的选项取决于数据库供应商和/或引擎。

### 常规

| 选项 | 描述 |
|---|---|
| Connection name | 此连接的名称 |
| Connection type | 此连接的描述 |
| Username |  |
| Password |  |
| Driver class | 用于此连接的类名。 |
| Manual Connection URL | 通常仅用于 generic 连接。 |

### 高级

| 选项 | 默认值 | 描述 |
|---|---|---|
| Supports the Boolean data type | true |  |
| Supports the Timestamp data type | true |  |
| Quote all identifiers in database |  |  |
| Force all identifiers to lower case |  |  |
| Force all identifiers to upper case |  |  |
| Reserve case of reserved words? | true |  |
| The preferred schema name |  |  |
| The SQL statements to run after connecting (; separated |  |  |

### 选项

以参数/值列表的形式指定额外的 JDBC 连接选项。

## 示例

无
