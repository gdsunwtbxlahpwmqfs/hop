# Neo4j 索引

## 描述

`Neo4j index` action 可用于在 Neo4j 图中的节点或关系属性上创建或删除索引。

有关 Neo4j 约束的类似操作，请查看 [Neo4j constraint](neo4j-constraint.md) action。

## 选项

- Neo4j Connection：要更新索引的 Neo4j 连接名称
- Index updates to perform：您可以在此指定索引更新列表
** Update type：CREATE 或 DROP
** Type of object to index on：NODE 或 RELATIONSHIP
** Index name：可选但推荐的索引名称
** Object name：要为其更新索引的节点或关系的标签
** Properties：要索引的节点或关系属性的逗号分隔列表

## 显示 Cypher 预览

Action 对话框包含一个 "Show Cypher" 按钮，显示将执行以创建或删除索引的 Cypher 语句预览。此预览在执行前显示确切的 `CREATE INDEX` 或 `DROP INDEX` 语句。

预览是只读的，帮助您验证将执行的索引操作。
