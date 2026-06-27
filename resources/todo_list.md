# Apache Hop JDBC 驱动下载清单

## 概述

Apache Hop 部分数据库驱动因许可证限制（如 GPL、商业许可证等）未包含在发行版中，需要用户手动下载。

本目录用于存放这些需要额外下载的 JDBC 驱动。

## 驱动分类

### 一、内置驱动（Hop 发行版已包含，无需下载）

| 数据库 | 驱动 | 版本 | Maven Artifact |
|--------|------|------|----------------|
| PostgreSQL | PostgreSQL JDBC | 42.7.11 | org.postgresql:postgresql |
| H2 | H2 Database | 2.3.232 | com.h2database:h2 |
| HSQLDB | HyperSQL | 2.7.4 | org.hsqldb:hsqldb |
| Derby | Apache Derby | 10.17.1.0 | org.apache.derby:derbyclient |
| DuckDB | DuckDB JDBC | 1.4.4.0 | org.duckdb:duckdb_jdbc |
| SQLite | SQLite JDBC | 3.51.2.0 | org.xerial:sqlite-jdbc |
| Redshift | Amazon Redshift JDBC | 2.2.2 | com.amazon.redshift:redshift-jdbc42 |
| MS SQL Server (Native) | Microsoft SQL Server JDBC | 13.4.0.jre11 | com.microsoft.sqlserver:mssql-jdbc |
| AS/400 (JT400) | JT400 | 21.0.6 | net.sf.jt400:jt400 |
| Firebird | Jaybird | - | org.firebirdsql.jdbc:jaybird |
| Hive | Hive JDBC | 4.0.0 | org.apache.hive:hive-jdbc |
| CockroachDB | CockroachDB JDBC | - | 内置支持 |
| CrateDB | CrateDB JDBC | 2.7.0 | io.crate:crate-jdbc |
| MonetDB | MonetDB JDBC | 12.0 | monetdb:monetdb-jdbc |
| Ingres | Ingres JDBC | - | 内置支持 |
| Netezza | Netezza JDBC | - | 内置支持 |
| Interbase | InterBase JDBC | - | 内置支持 |
| SAP DB | SAP DB JDBC | - | 内置支持 |
| Universe | Universe JDBC | - | 内置支持 |
| Cache (Intersystems) | Cache JDBC | - | 内置支持 |
| IRIS (Intersystems) | IRIS JDBC | - | 内置支持 |
| KingbaseES | KingbaseES JDBC | - | 内置支持 |
| VectorWise | VectorWise JDBC | - | 内置支持 |
| GreenPlum | GreenPlum JDBC | - | 内置支持 |
| Infobright | Infobright JDBC | - | 内置支持 |
| Oracle RDB | Oracle RDB JDBC | - | 内置支持 |

### 二，自动下载的驱动（脚本已包含）

运行 `./download_shared_jars.sh` 自动下载以下驱动：

| 数据库 | 驱动 | 版本 | 来源 | 许可证 |
|--------|------|------|------|--------|
| MySQL | MySQL Connector/J | 9.1.0 | Maven Central | GPL |
| MariaDB | MariaDB Connector/J | 3.5.2 | Maven Central | LGPL |
| Snowflake | Snowflake JDBC | 4.2.0 | Maven Central | Apache 2.0 |
| Snowflake 依赖 | Bouncy Castle Provider | 1.79 | Maven Central | MIT |
| Snowflake 依赖 | Bouncy Castle PKIX | 1.79 | Maven Central | MIT |
| Sybase ASE / MSSQL | jTDS | 1.3.1 | Maven Central | LGPL |
| DB2 | IBM JCC Driver | 11.5.9.0 | Maven Central | IBM License |
| Vertica | Vertica JDBC | 25.3.0-0 | Maven Central | Vertica License |
| ClickHouse | ClickHouse JDBC | 0.6.0 | Maven Central | Apache 2.0 |
| Oracle | Oracle JDBC (ojdbc11) | 23.8.0.25.04 | Oracle 官网 | Oracle FDHUT |
| MS Access | - | - | - | 见下文 |
| Sybase IQ | - | - | - | 见下文 |
| Informix | - | - | - | 见下文 |
| SQLBase | - | - | - | 见下文 |
| SingleStore | - | - | - | 见下文 |
| Exasol4 | - | - | - | 见下文 |
| Generic | - | - | - | 通用 JDBC |

### 三，需要手动下载的驱动

| 数据库 | 驱动 | 下载地址 | 许可证 | 备注 |
|--------|------|----------|--------|------|
| **Google BigQuery** | Simba JDBC Driver | https://cloud.google.com/bigquery/docs/reference/odbc-jdbc-drivers | Google Terms | 官方推荐 |
| **Teradata** | Teradata JDBC Driver | https://downloads.teradata.com/download/connectivity/jdbc-driver | Teradata License | - |
| **MS Access** | UCanAccess / JDBC-ODBC | https://ucanaccess.sourceforge.io/site.html | LGPL | 或使用 jdbc:odbc 桥接 |
| **Sybase IQ** | SAP Sybase IQ JDBC | https://support.sap.com/ | SAP License | 需 SAP 账号 |
| **Informix** | IBM Informix JDBC | https://www.ibm.com/products/informix | IBM License | 需 IBM 账号 |
| **SQLBase** | Gupta SQLBase JDBC | https://www.opentext.com/ | OpenText License | - |
| **SingleStore** | SingleStore JDBC | https://www.singlestore.com/ | SingleStore License | 兼容 MySQL 协议 |
| **Exasol4** | Exasol JDBC | https://www.exasol.com/ | Exasol License | - |

### 四，已下载的驱动

| 数据库 | 驱动文件名 | 大小 |
|--------|-----------|------|
| MySQL | mysql-connector-j-9.1.0.jar | 2.5M |
| MySQL | mysql-connector-j-8.4.0.jar | 2.4M |
| MariaDB | mariadb-java-client-3.5.2.jar | 726K |
| Snowflake | snowflake-jdbc-4.2.0.jar | 96M |
| Snowflake | snowflake-jdbc-3.19.0.jar | 68M |
| Bouncy Castle | bcprov-jdk18on-1.79.jar | 8.2M |
| Bouncy Castle PKIX | bcpkix-jdk18on-1.79.jar | 1.1M |
| jTDS (Sybase/SQL Server) | jtds-1.3.1.jar | 310K |
| DB2 | jcc-11.5.9.0.jar | 6.2M |
| Vertica | vertica-jdbc-25.3.0-0.jar | 1.8M |
| ClickHouse | clickhouse-jdbc-0.6.0.jar | 1.2M |
| Oracle | ojdbc11-23.8.0.25.04.jar | 7.0M |

## 使用说明

### 自动下载

运行以下命令自动下载所有可自动下载的驱动：

```bash
./download_shared_jars.sh
```

### 手动下载

1. 访问上表中列出的下载地址
2. 下载对应的 JDBC 驱动 jar 文件
3. 将下载的 jar 文件放到 `jdbc/` 目录中

### Docker 环境配置

确保 `docker-compose.dev.yml` 中的 `HOP_SHARED_JDBC_FOLDERS` 指向本目录：

```yaml
environment:
  HOP_SHARED_JDBC_FOLDERS: /opt/hop/jdbc
volumes:
  - ./resources/jdbc:/opt/hop/jdbc
```

## 许可证说明

- **GPL 驱动**：MySQL Connector/J 等使用 GPL 许可证，Apache 项目不能直接分发
- **LGPL 驱动**：MariaDB Connector/J、jTDS 等使用 LGPL
- **商业驱动**：Oracle、DB2、Teradata、Sybase IQ 等使用专有许可证
- **MIT/Apache 驱动**：Bouncy Castle、ClickHouse 等宽松许可证

请在下载和使用这些驱动前阅读并理解相关的许可证条款。
