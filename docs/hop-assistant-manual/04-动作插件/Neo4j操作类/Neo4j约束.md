# Neo4j约束（Neo4j constraint）

`Neo4j constraint` 动作可用于在 Neo4j 图中的节点或关系属性上创建或删除约束。

有关 Neo4j 索引的类似操作，请查看 Neo4j 索引动作。

## 主要选项

- **Neo4j 连接（Neo4j Connection）**：要更新索引的 Neo4j 连接名称。
- **要执行的约束更新（Constraint updates to perform）**：您可以在此指定约束更新列表
  - 更新类型：CREATE 或 DROP
  - 索引对象类型：NODE 或 RELATIONSHIP
  - 约束类型：UNIQUE、NOT_NULL 或 NODE_KEY
  - 约束名称：约束名称（必填）
  - 对象名称：要更新约束的节点或关系的标签
  - 属性：要放置约束的属性（对于 NODE_KEY，使用逗号分隔的属性，如"id,name"）
