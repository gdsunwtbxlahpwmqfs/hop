# Neo4j 约束

## 描述

`Neo4j constraint` action 可用于在 Neo4j 图中的节点或关系属性上创建或删除约束。

有关 Neo4j 索引的类似操作，请查看 [Neo4j index](workflow/actions/neo4j-index.md) action。

## 选项

- Neo4j Connection：要更新索引的 Neo4j 连接名称
- Constraint updates to perform：您可以在此指定约束更新列表
** Update type：CREATE 或 DROP
** Type of object to index on：NODE 或 RELATIONSHIP
** Type of constraint：UNIQUE、NOT_NULL 或 NODE_KEY
** Constraint name：约束名称（必填）
** Object name：要为其更新约束的节点或关系的标签
** Property：要放置约束的属性（对于 NODE_KEY，使用逗号分隔的属性，如 "id,name"）

> **📝 注意:** NODE_KEY 约束仅在 Neo4j Enterprise Edition 中可用。NODE_KEY 约束需要多个属性（逗号分隔），并且只能应用于节点，不能应用于关系。

## 显示 Cypher 预览

Action 对话框包含一个 "Show Cypher" 按钮，显示将执行以创建或删除约束的 Cypher 语句预览。此预览在执行前显示确切的 `CREATE CONSTRAINT` 或 `DROP CONSTRAINT` 语句。

预览是只读的，帮助您验证将执行的约束操作，包括 UNIQUE、NOT_NULL 和 NODE_KEY 约束的正确语法。
