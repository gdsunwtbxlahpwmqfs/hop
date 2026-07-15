# Cassandra 技术

来自 [Apache Cassandra](https://cassandra.apache.org/) 网站：

当您需要可扩展性和高可用性而不牺牲性能时，这个数据库是正确的选择。[线性可扩展性](http://techblog.netflix.com/2011/11/benchmarking-cassandra-scalability-on)和在商用硬件或云基础设施上经过验证的容错能力使其成为关键任务数据的完美平台。
Cassandra 对跨多个数据中心复制的支持是一流的，为您的用户提供更低的延迟，并让您安心地知道您可以应对区域性的服务中断。

来自 [Wikipedia](https://en.wikipedia.org/wiki/Apache_Cassandra)：

Apache Cassandra 是一个免费且开源的分布式宽列存储 NoSQL 数据库管理系统，旨在处理跨多台商用服务器的大量数据，提供高可用性且无单点故障。
Cassandra 支持跨越多个数据中心的集群，[2] 通过异步无主复制为所有客户端提供低延迟操作。
Cassandra 的设计结合了 Amazon Dynamo 的分布式存储和复制技术以及 Google Bigtable 的数据和存储引擎模型。[3]

## Hop 中的 Cassandra 支持

Hop 支持 Cassandra 4（自 2.2.0 版本起），提供以下内容：

### Metadata 类型

- [Cassandra 连接](../06-元数据类型/cassandra-connection.md)：创建到您的 Cassandra 数据库集群的连接。

### Workflow Action

- [Cassandra 执行 CQL](../04-动作插件/其他动作/cassandra-exec-cql.md)：执行 Cassandra [CQL](https://cassandra.apache.org/doc/latest/cql/)

### Pipeline Transform

- [Cassandra Input](../03-转换插件/输入类/cassandra-input.md)：通过 CQL 查询从 Cassandra 集群读取数据。
- [Cassandra Output](../03-转换插件/其他转换/cassandra-output.md)：将数据写入 Cassandra 集群中的表。
- [SSTable Output](../03-转换插件/其他转换/sstable-output.md)：将数据作为 Cassandra SSTable 写入文件系统目录。
