# Databricks

请查看 [Databricks 文档](https://docs.databricks.com/aws/en/integrations/compute-details)了解如何获取连接详情以指定您的 catalog。

> **⚠️ 警告:** Databricks 连接中不使用数据库名称字段。请使用 Catalog Name 字段。

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | [驱动链接](https://docs.databricks.com/aws/en/integrations/jdbc/download) |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://docs.databricks.com/aws/en/integrations/jdbc/) |
| JDBC Url | jdbc:databricks://<server-hostname>:443;httpPath=<http-path>[;<setting1>=<value1>;<setting2>=<value2>;<settingN>=<valueN>] |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |
