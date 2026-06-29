# Cassandra SSTable 输出

Cassandra SSTable 输出（Cassandra SSTable Output）转换以 Cassandra SSTable 格式写入文件系统目录。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| Cassandra 连接 | Cassandra 连接配置 |
| 键空间 | 目标键空间 |
| 目录 | 输出 SSTable 文件的目录 |
| 字段映射 | 输入字段到 Cassandra 列的映射 |

## 注意事项

- 该转换将数据写为 Cassandra SSTable 文件格式，而非直接写入 Cassandra 集群。
- 生成的 SSTable 文件可后续通过 Cassandra 工具加载到集群中。
- 适用于离线数据生成和批量导入场景。
