# Memgraph

## 描述

[Memgraph](https://www.memgraph.com) 在很大程度上与 Neo4j 兼容，可以使用 Qi Hop 中的许多 Neo4j 功能。Memgraph 专注于内存中、实时和可扩展的分析。

Memgraph 与 Neo4j 的兼容性使您能够在 Qi Hop 中使用大量 Neo4j 功能来操作 Memgraph。

## 在 Docker 中运行 Memgraph

需要设置 `--bolt-server-name-for-init=Neo4j/` 参数，以允许 Qi Hop 通过 `bolt` 协议连接到 Memgraph 数据库。更多信息请参阅 [Memgraph 文档](https://memgraph.com/docs/memgraph/connect-to-memgraph/drivers/java)。

在容器中启动 Memgraph 服务器的示例命令：

`docker run -it -p 7687:7687 -p 7444:7444 -p 3000:3000 -e MEMGRAPH="--bolt-server-name-for-init=Neo4j/" memgraph/memgraph-platform`

## 可用的功能

- [Neo4j 连接](metadata-types/neo4j/neo4j-connection.md)：确保指定 `bolt` 协议而不是默认的 `neo4j` 协议
- 大多数 [Neo4j graph output](pipeline/transforms/neo4j-graphoutput.md)、[Neo4j output](pipeline/transforms/neo4j-output.md)、[Cypher transform](pipeline/transforms/neo4j-cypher.md) 和 [action](workflow/actions/neo4j-cypherscript.md)（除了索引操作，见下文）似乎都能正常工作。如果您发现任何问题，请创建一个 [bug 工单](https://github.com/apache/hop/issues/)。

## 已知问题

Memgraph 的索引语法与 Neo4j 语法不同，这导致 [Neo4j index](workflow/actions/neo4j-index.md) 和 [Neo4j constraint](workflow/actions/neo4j-constraint.md) action 失败。

通过 Cypher action 或 Cypher transform 创建索引会失败，错误类似于 `ERROR: org.neo4j.driver.exceptions.ClientException: Index manipulation not allowed in multicommand transactions.`。

查看 [Memgraph 文档](https://memgraph.com/docs/memgraph/reference-guide/indexing)了解更多索引信息。
