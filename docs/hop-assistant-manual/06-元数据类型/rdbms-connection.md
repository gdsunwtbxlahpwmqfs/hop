# 关系型数据库连接

## 描述

![](../assets/images/icons/database.svg)

描述连接到关系型数据库所需的所有信息。

Hop 开箱即支持数十种关系型数据库。如果你的特定数据库没有内置支持，你可能仍然可以使用 Generic 数据库连接进行连接。

查看 [数据库](database/databases.md)列表了解更多详情。

## 相关 plugin

Action：

- [Check DB Connections](workflow/actions/checkdbconnection.md)
- [Columns exist in table](workflow/actions/columnsexist.md)
- [MS SQL Bulk Loader](workflow/actions/mssqlbulkload.md)
- [MySQL Bulk File](workflow/actions/mysqlbulkfile.md)
- [MySQL Bulk Loader](workflow/actions/mysqlbulkload.md)
- [SQL](workflow/actions/sql.md)
- [Table Exists](workflow/actions/tableexists.md)
- [Truncate Tables](workflow/actions/truncatetables.md)
- [Wait for SQL](workflow/actions/waitforsql.md)

Transform：

- [Call DB Procedure](pipeline/transforms/calldbproc.md)
- [Column Exists](pipeline/transforms/columnexists.md)
- [Combination Lookup/Update](pipeline/transforms/combinationlookup.md)
- [Database Join](pipeline/transforms/databasejoin.md)
- [Database Lookup](pipeline/transforms/databaselookup.md)
- [Dynamic SQL Row](pipeline/transforms/dynamicsqlrow.md)
- [Execute SQL Row](pipeline/transforms/execsqlrow.md)
- [Insert/Update](pipeline/transforms/insertupdate.md)
- [Oracle Bulk Loader](pipeline/transforms/orabulkloader.md)
- [PostgreSQL Bulk Loader](pipeline/transforms/postgresbulkloader.md)
- [Synchronize After Merge](pipeline/transforms/synchronizeaftermerge.md)
- [Table Compare](pipeline/transforms/tablecompare.md)
- [Table Exists](pipeline/transforms/tableexists.md)
- [Table Input](pipeline/transforms/tableinput.md)
- [Table Output](pipeline/transforms/tableoutput.md)
- [Teradata Bulk Loader](pipeline/transforms/terafast.md)
- [Update](pipeline/transforms/update.md)

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
