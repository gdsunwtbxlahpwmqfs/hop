# Cloudera Impala（Cloudera Impala）

## 概述

您可以使用 Impala 提供的低延迟 SQL 实现来查询 Hadoop 上的数据。更多信息请参见 https://docs.cloudera.com/runtime/7.2.18/impala-overview/topics/impala-overview.html 的 Apache Impala 概述。

Impala 本身已成为一个 Apache 项目，您可以在 https://impala.apache.org 的 Apache Impala 主页找到相关信息。

使用 Kerberos 安全机制连接 Cloudera Impala 时，请查阅 https://docs.cloudera.com/documentation/other/connectors/impala-jdbc/latest.html 的文档，并在关系型数据库连接元数据的选项（Options）标签页中设置所需选项：

* AllowSelfSignedCerts=1
* AuthMech=1
* KrbHostFQDN=`IMPALA HOSTNAME`
* KrbRealm=`Kerberos Realm`
* KrbServiceName=`Impala Service name`
* SSL=1
* principal=`Principal name`

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 使用 Cloudera Impala 驱动。您可以从 Cloudera 官网 https://www.cloudera.com/downloads/connectors/impala/jdbc/2-6-35.html 下载最新版本 |
| 内置版本 | 无 |
| Hop 依赖 | Cloudera Impala 数据库插件 |
| 文档 | https://docs.cloudera.com/documentation/other/connectors/impala-jdbc/latest.html |
| JDBC 连接串 | jdbc:impala://hostname:port;principal=principalName |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
