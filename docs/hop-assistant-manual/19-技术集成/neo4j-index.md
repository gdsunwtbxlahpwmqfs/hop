# Neo4j

## 描述

Neo4j 是一个开源图数据库，您可以从 **[www.neo4j.com/download-center](https://neo4j.com/download-center/)** 下载。

您可以使用它在属性图中用节点和关系来表示信息。
Neo4j 不使用索引，这使其能够通过所谓的图算法非常快速地遍历大型图。
有关这些独特图算法的更多信息，请参见：[Neo4j 图算法](https://neo4j.com/docs/graph-data-science/current/algorithms/#algorithms)

Hop 通过以下 metadata 对象支持 Neo4j

## 视图：执行血缘

您可以使用 Neo4j 存储 workflow 和 pipeline 的日志和执行血缘信息。
您只需将变量 `NEO4J_LOGGING_CONNECTION` 设置为要将日志和血缘写入的 Neo4j 连接名称即可。

Neo4j plugin 提供了一个单独的视图来查询这些日志和血缘信息。
例如，它允许您快速跳转到发生错误的位置。
这个巧妙的技巧是通过请求数据库找到发生错误且没有子节点的执行节点与"祖父"节点之间的最短路径来实现的。
您获得的路径正是从例如"祖父"workflow 到发生错误的确切 transform 所遵循的路径。

## Metadata 类型

- [Neo4j 连接](metadata-types/neo4j/neo4j-connection.md)：创建或管理到 Neo4j 数据库的连接
- [Neo4j 图模型](metadata-types/neo4j/neo4j-graphmodel.md)：创建或管理 Neo4j（子）图模型

## Workflow Action

- [Neo4j 检查连接](workflow/actions/neo4j-checkconnections.md)：检查到 Neo4j 数据库的连接
- [Neo4j Cypher 脚本](workflow/actions/neo4j-cypherscript.md)：执行 Cypher 脚本或查询
- [Neo4j 索引](workflow/actions/neo4j-index.md)：创建或删除 Neo4j 索引
- [Neo4j 约束](workflow/actions/neo4j-constraint.md)：创建或删除 Neo4j 约束

## Pipeline Transform

- [Neo4j Cypher](pipeline/transforms/neo4j-cypher.md)：使用输入字段的参数信息在 Neo4j 数据库上执行 Cypher 查询
- [Neo4j 生成 CSV](pipeline/transforms/neo4j-gencsv.md)：为节点和关系生成 CSV 文件，以便与 neo4j-import 一起使用
- [Neo4j 获取日志信息](pipeline/transforms/neo4j-getloginfo.md)：查询 Neo4j 日志图以获取执行信息
- [Neo4j Graph Output](pipeline/transforms/neo4j-graphoutput.md)：使用输入字段映射写入 Neo4j 图。
- [Neo4j Import](pipeline/transforms/neo4j-import.md)：使用提供的 CSV 文件运行 Neo4j 导入命令
- [Neo4j Output](pipeline/transforms/neo4j-output.md)：将节点和/或关系写入 Neo4j 图
- [Neo4j Split Graph](pipeline/transforms/neo4j-split-graph.md)：拆分 Neo4j 图的节点和关系

## 示例

发行下载中包含的 Neo4j 示例在以下文档中描述：

[使用 Neo4j 数据](technology/neo4j/working-with-neo4j-data.md)

[Neo4j 日志 Schema](technology/neo4j/neo4j-logging-schema.md) 描述了将执行日志记录到 Neo4j 时使用的图 schema。
