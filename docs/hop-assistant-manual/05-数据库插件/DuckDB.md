# DuckDB（DuckDB）

## 概述

DuckDB 是一个进程内（in-process）的 SQL OLAP 数据库管理系统。

作为一个进程内数据库，DuckDB 的配置非常简单：只需将 DuckDB 文件的路径指定为数据库名称即可，例如 `<PATH_TO_YOUR_DUCKDB_FILE>/duckdb`。

关于 DuckDB 如何管理并发，需要记住以下几点：

* 同一时间只有一个进程可以对数据库进行读写。
* 多个进程可以读取数据库，但不能有进程进行写入。要设置此行为，请记住在连接的选项中指定属性 `duckdb.read_only = true`。

更多详情，请参阅 https://duckdb.org/docs/api/java 的 DuckDB Java API 文档。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://search.maven.org/artifact/org.duckdb/duckdb_jdbc/1.3.2.1/jar |
| 内置版本 | 1.4.4.0 |
| Hop 依赖 | 无 |
| 文档 | https://duckdb.org/docs/api/java |
| JDBC 连接串 | jdbc:duckdb:&lt;path&gt; |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
