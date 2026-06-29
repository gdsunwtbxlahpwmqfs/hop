# Neo4j索引（Neo4j index）

`Neo4j index` 动作可用于在 Neo4j 图中的节点或关系属性上创建或删除索引。

有关 Neo4j 约束的类似操作，请查看 Neo4j 约束动作。

## 主要选项

- **Neo4j 连接（Neo4j Connection）**：要更新索引的 Neo4j 连接名称。
- **要执行的索引更新（Index updates to perform）**：您可以在此指定索引更新列表
  - 更新类型：CREATE 或 DROP
  - 索引对象类型：NODE 或 RELATIONSHIP
  - 索引名称：可选但推荐的索引名称
  - 对象名称：要更新索引的节点或关系的标签
  - 属性：要索引的节点或关系属性的逗号分隔列表

## 显示 Cypher 预览
