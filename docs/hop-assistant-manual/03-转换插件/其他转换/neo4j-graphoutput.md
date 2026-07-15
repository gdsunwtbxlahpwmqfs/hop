# ![Neo4j Graph Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/neo4j_graph_output.svg) Neo4j Graph Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 限制

- 不支持跨未知节点 `()--()` 的更新

## 建模提示

- 尝试使用 [Cypher Workbench](https://neo4j.solutions/cypher-workbench) 或 [Arrows](https://arrows.app) 等建模工具，并将结果导入 Graph Model。
- 尽可能保持模型简单。
- 确保每个定义的节点都有主键字段
- 每个节点限制为一个主键字段

## 执行提示

- 确保每个节点主键都有索引。
使用 graph model 编辑器中提供的按钮生成 Neo4j Index 和 Constraint action，它们会为您处理此操作。

## 重要选项

| 选项 | 描述 |
|---|---|
| Transform name | 此 Transform 在 Pipeline 中的名称 |
| Neo4j connection | 要将图形写入的 Neo4j 连接 |
| Graph model | 要使用的 [Neo4j graph model](../../06-元数据类型/neo4j-graphmodel.md) |
| Batch size (rows) | 向 Neo4j 写入数据时使用的批次大小 |
| Create indexes? | 在运行此 Transform 之前创建 Neo4j 索引 |
| Return graph data? | 返回 Neo4j 返回的图形数据 |
| Graph output field name | 写入图形数据的字段名称（如果启用了 `Return graph data?`） |
| Validate against model? | 根据模型验证数据。 |
| Allow out of order updates? | 如果允许乱序更新，此 Transform 会生成 unwind 语句。 |
| Field to property mappings 选项卡 |  |
| 在此选项卡中可以指定输入字段在图形中的最终位置。 |  |
| Field to relationship mappings 选项卡 |  |
| 有时您需要根据输入数据选择图形模型中的关系。 |  |
| Node label mappings 选项卡 |  |
| 如果要从图形模型中指定的标签中选择特定标签，可以在此选项卡中创建映射条目。 |  |

## 查看 Cypher 预览

Transform 对话框包含一个 "Show Cypher" 按钮，显示将生成的通用 Cypher 模式预览。由于 Graph Output 会根据输入行数据和字段映射动态生成 Cypher，预览显示：

- 通用模式和结构
- Graph Model 详情（如果可用）
- 示例 Cypher 结构
- 处理模式（按行或基于 UNWIND（如果允许乱序更新））

预览是只读的，帮助您理解 Transform 将如何根据您的配置生成 Cypher 语句。
