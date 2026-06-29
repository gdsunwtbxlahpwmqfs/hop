# Neo4j 导入（Neo4j Import）

Neo4j Import 转换使用提供的 CSV 文件运行导入命令。

该转换基于 Neo4j 提供的 `neo4j-admin-import` 工具实现批量数据导入。完整细节请查阅官方 neo4j-admin-import 文档。

该转换同时支持 Neo4j 4.x 和 5.x 语法，可通过 "Neo4j version" 选项选择相应版本。由于 Neo4j 5.x 对 `neo4j-admin` 命令语法做了改动，转换会自动调整生成的命令。此外，在适当配置下，该转换还支持在 Docker 容器中运行 `neo4j-admin` 命令。

## 主要选项

| 选项 | 说明 |
|------|------|
| 连接 | 指定 Neo4j 数据库连接 |
| Neo4j 版本 | 选择 Neo4j 4.x 或 5.x，以匹配相应的命令语法 |
| CSV 文件 | 指定用于导入的节点和关系 CSV 文件 |
| 导入目录 | 指定 Neo4j 的 import 文件夹路径 |
| Docker 配置 | 配置在 Docker 容器中运行 neo4j-admin 命令的相关参数 |
