# Microsoft Access

创建新的数据库连接时，请在数据库名称字段中指定 .mdb 或 .accdb 文件的数据库路径。

此数据库连接使用 UCanAccess 驱动，该驱动在通过 TableInput、DatabaseLookup 读取时表现良好，但不适合写入操作。
对于写入操作，最好使用 Microsoft Access Output transform。

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | 已内置 |
| 内置版本 | 5.1.5 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://spannm.github.io/ucanaccess/10-ucanaccess) |
| JDBC Url | jdbc:ucanaccess://path.mdb |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |
