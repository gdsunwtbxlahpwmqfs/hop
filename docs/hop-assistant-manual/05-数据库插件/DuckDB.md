# DuckDB

DuckDB 是一个进程内 SQL OLAP 数据库管理系统。

作为进程内数据库，DuckDB 配置简单：将 DuckDB 文件名的路径指定为数据库名称即可，例如 `<PATH_TO_YOUR_DUCKDB_FILE>/duckdb`。

需要注意的一点是 DuckDB 如何管理并发。需要记住的要点总结如下：

- 同一时间只有一个进程可以同时读写数据库。
- 多个进程可以读取数据库，但不能有进程写入。要设置此行为，请记住在连接的 options 中指定属性 duckdb.read_only = true

详情请参阅 [DuckDB Java API 文档](https://duckdb.org/docs/api/java)。

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | [驱动链接](https://search.maven.org/artifact/org.duckdb/duckdb_jdbc/1.3.2.1/jar) |
| 内置版本 | 1.4.4.0 |
| Hop 依赖 | 无 |
| 文档 | https://duckdb.org/docs/api/java.html |
| JDBC Url | jdbc:duckdb:（内存模式）或 jdbc:duckdb:<FILE_PATH> |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |
