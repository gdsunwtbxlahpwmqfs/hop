# Cloudera Impala

您可以使用 Impala 的低延迟 SQL 实现来查询 Hadoop 上的数据。
更多信息请参见 [Apache Impala 概览](https://docs.cloudera.com/runtime/7.2.18/impala-overview/topics/impala-overview)

Impala 本身已成为一个 Apache 项目，您可以在 [Apache Impala 主页](https://impala.apache.org)找到它。

请查看[文档](https://docs.cloudera.com/documentation/other/connectors/impala-jdbc/latest)了解如何使用 Kerberos 安全连接到 Cloudera Impala。在关系型数据库连接 metadata 的 Options 选项卡中设置所需选项：

- AllowSelfSignedCerts=1
- AuthMech=1
- KrbHostFQDN=`IMPALA HOSTNAME`
- KrbRealm=`Kerberos Realm`
- KrbServiceName=`Impala Service name`
- SSL=1
- principal=`Principal name`

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | 使用 Cloudera Impala 驱动。您可以从 Cloudera 网站[下载](https://www.cloudera.com/downloads/connectors/impala/jdbc/2-6-35)最新版本。 |
| 内置版本 | 无 |
| Hop 依赖 | Cloudera Impala 数据库插件 |
| 文档 | [文档链接](https://docs.cloudera.com/documentation/other/connectors/impala-jdbc/latest) |
| JDBC Url | jdbc:impala://hostname:port;principal=principalName |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |
