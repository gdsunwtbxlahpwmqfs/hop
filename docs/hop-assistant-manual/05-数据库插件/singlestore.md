# SingleStore (MemSQL)

您可以在 [SingleStore 主页](https://www.singlestore.com/)了解更多关于 SingleStore 数据库的信息。
SingleStore 在语法上是 MySQL 的一个变体，您可以对 SingleStore 使用 MySQL bulk loader 等 action。

有关 SingleStore JDBC 驱动的更多信息，请访问：[SingleStore JDBC 驱动](https://docs.singlestore.com/cloud/developer-resources/connect-with-application-development-tools/connect-with-java-jdbc/the-singlestore-jdbc-driver/)。

*重要*：您需要自行下载并安装 SingleStore JDBC 驱动。
我们建议您将驱动 JAR 文件放在 `lib/jdbc` 文件夹或由 [`HOP_SHARED_JDBC_FOLDERS`](snippets/variables/hop-shared-jdbc-folder.md) 变量指向的文件夹中。

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | 从 SingleStore 网站下载 |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://docs.singlestore.com/cloud/developer-resources/connect-with-application-development-tools/connect-with-java-jdbc/the-singlestore-jdbc-driver/) |
| JDBC Url | jdbc:singlestore://<database_computer>[:<port>]/<database_name> |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |
