# Neo4j 生成 CSV（Neo4j Generate CSVs）

Neo4j Generate CSV 转换将节点和关系文件写入 import 文件夹，供 Neo4j 的 neo4j-import 工具使用。

该转换生成符合 Neo4j 导入工具格式要求的 CSV 文件，是进行大规模离线数据导入 Neo4j 图数据库的前置步骤。生成的文件可直接被 `neo4j-admin import` 命令使用。

## 主要选项

| 选项 | 说明 |
|------|------|
| 输出目录 | 指定生成的 CSV 文件输出路径（通常为 Neo4j 的 import 文件夹） |
| 节点文件 | 配置节点数据 CSV 文件的生成规则及字段映射 |
| 关系文件 | 配置关系数据 CSV 文件的生成规则及字段映射 |
| 文件格式 | 指定 CSV 文件的分隔符、引用符等格式选项 |
