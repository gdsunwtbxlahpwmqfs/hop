# Neo4j 获取日志信息（Neo4j Get Logging Info）

Neo4j Get Logging Info 转换用于查询 Neo4j 日志图并检索日志信息。

该转换使用 `NEO4J_LOGGING_CONNECTION` 连接配置来访问日志数据。更多详细信息请查阅 Neo4j 透视视图（Neo4j perspective）相关文档。它适用于从 Neo4j 中提取运行日志、审计信息等场景。

## 主要选项

| 选项 | 说明 |
|------|------|
| 连接 | 使用 NEO4J_LOGGING_CONNECTION 配置连接到 Neo4j 日志图 |
| 查询参数 | 配置用于筛选日志信息的查询条件 |
| 输出字段 | 指定从日志图中检索并输出的字段 |
