# 元数据类型

元数据是 Hop 的基石之一，可以定义为 Workflow、Pipeline 和任何其他类型的元数据对象。

Hop GUI 有一个 Metadata Perspective 来管理所有类型的元数据：运行配置、数据库（关系型和 NoSQL）连接、日志以及 Pipeline 探针等。

元数据通常作为 JSON 文件存储在项目的 metadata 文件夹中，按元数据类型分放在不同的子文件夹中。
唯一的例外是 Workflow 和 Pipeline，它们被定义为 XML（目前如此，由于历史原因）。
由于 Workflow 和 Pipeline 是 Hop 的核心，它们通常存储在你的项目文件夹中，而不是项目的 metadata 文件夹中。

> **💡 提示:** 我们已尽可能简化在 Hop 中添加或删除 plugin 的操作。
由于元数据类型也是 plugin 类型，你的 Hop 安装中可用的元数据类型可能与此列表不完全匹配。

默认情况下，Hop 包含以下元数据类型：

- [异步 Web Service](hop-server/async-web-service.md)：通过 Web Service 异步执行和查询 Workflow。
- [Azure Blob Storage 认证](metadata-types/azure-authentication.md)：Azure Blob Storage 连接类型。
- [Beam 文件定义](metadata-types/beam-file-definition.md)：描述 Beam Pipeline 中的文件布局
- [Cassandra 连接](metadata-types/cassandra/cassandra-connection.md)：描述到 Cassandra 集群的连接
- [Data Set](metadata-types/data-set.md)：定义一个 data set，即静态预定义的行集合
- [Data Stream](metadata-types/data-stream/data-stream.md)：Data Stream 可用于进程间通信（IPC）或快速数据序列化。
- [Execution Data Profile](metadata-types/execution-data-profile.md)：使用可配置的采样器在数据流经 Pipeline 时收集和分析数据，以了解值范围、空值和行样本。
- [Execution Information Location](metadata-types/execution-information-location.md)：定义 Qi Hop 存储执行元数据的位置和方式，支持本地文件、远程服务器、Neo4j 或 Elastic，以便后续检查和分析。
- [Google Storage 认证](metadata-types/google-storage-authentication.md)：Google Cloud Storage 连接类型。
- [Hop Server](metadata-types/hop-server.md)：定义 Hop Server
- [MongoDB 连接](metadata-types/mongodb-connection.md)：描述 MongoDB 连接
- [邮件服务器连接](metadata-types/mail-server-connection.md)：描述邮件服务器连接
- [Minio (S3) 连接](metadata-types/minio-connection.md)：使用 Minio 客户端连接到一个或多个 S3 端点
- [WebDAV 连接](metadata-types/webdav-connection.md)：命名的 WebDAV 或基于 HTTPS 的 WebDAV 端点，用作文件路径中的 VFS 方案
- [Neo4j 连接](metadata-types/neo4j/neo4j-connection.md)：到 Neo4j 服务器的共享连接
- [Neo4j 图模型](metadata-types/neo4j/neo4j-graphmodel.md)：描述 Neo4j 图的节点、关系、索引等
- [Partition Schema](metadata-types/partition-schema.md)：描述分区 schema
- [Pipeline 日志](metadata-types/pipeline-log.md)：允许用另一个 Pipeline 记录 Pipeline 的活动
- [Pipeline 探针](metadata-types/pipeline-probe.md)：允许将 Pipeline 的输出行流式传输到另一个 Pipeline
- [Pipeline 运行配置](metadata-types/pipeline-run-config.md)：描述 Pipeline 如何以及使用哪个引擎执行
- [Pipeline 单元测试](metadata-types/pipeline-unit-test.md)：描述 Pipeline 的测试，使用替代数据集作为某个 transform 的输入，并将输出与黄金数据进行测试
- [关系型数据库连接](metadata-types/rdbms-connection.md)：描述连接到关系型数据库所需的所有元数据
- [REST 连接](metadata-types/rest-connection.md)：描述连接到 REST API 所需的所有元数据。
- [Salesforce 连接](metadata-types/salesforce-connection.md)：描述一个可跨多个 Salesforce transform 使用的可重用 Salesforce 连接
- [Splunk 连接](metadata-types/splunk-connection.md)：描述 Splunk 连接
- [静态 Schema 定义](metadata-types/static-schema-definition.md)：定义可重用的数据流布局，以确保跨多个 Pipeline 的一致性并简化 schema 管理。
- [变量解析器](metadata-types/variable-resolver/index.md)：使用 plugin 通过 Pipeline、密钥库、vault 或密钥管理器来解析变量值。
- [Web Service](hop-server/web-service.md)：允许运行 Pipeline 来为 Hop Server 上的 servlet 生成输出
- [Workflow 日志](metadata-types/workflow-log.md)：允许用 Pipeline 记录 Workflow 的活动
- [Workflow 运行配置](metadata-types/workflow-run-config.md)：描述如何运行 Workflow
