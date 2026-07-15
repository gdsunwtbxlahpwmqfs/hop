## 项目 Metadata

项目的 metadata 通常包含若干数据库连接（关系型或 NoSQL）、日志配置等。
此 metadata 存储在项目级别，可通过 metadata 视图访问。

通常处理 metadata 的方式是：

- metadata 对象（连接、日志等）在项目级别定义。
例如，在项目中创建一个 `CRM` 数据库连接，使用若干变量来指定主机名（`{openvar}CRM_HOST{closevar}`）和数据库名（`{openvar}CRM_DBNAME{closevar}`），以及相应的端口、用户名、密码等变量。
- metadata 对象的_配置_在环境级别定义，在此创建若干包含 `{openvar}CRM_HOST{closevar}` 等 IP 地址的变量_值_。

项目的 metadata 存储在 `{openvar}PROJECT_HOME{closevar}/metadata` 中，但可以通过在项目配置对话框中设置项目的 `metadata base folder {openvar}HOP_METADATA_FOLDER{closevar}` 属性，或直接在 `project-config.json` 文件中设置来覆盖。

基本的项目 metadata 文件夹类似于以下结构：

```bash
.
├── cassandra-connection
├── dataset
├── file-definition
├── git
├── mongodb-connection
├── neo4j-connection
├── neo4j-graph-model
├── partition
├── pipeline-log
├── pipeline-probe
├── pipeline-run-configuration
│   ├── local.json
│   └── Spark.json
├── rdbms
│   └── crm.json
├── schema-definition
├── server
├── splunk
├── unit-test
├── web-service
├── workflow-log
└── workflow-run-configuration
    └── local.json
18 directories, 4 files
```
