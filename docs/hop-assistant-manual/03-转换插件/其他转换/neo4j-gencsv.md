# ![Neo4j Generate CSVs transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/neo4j_load.svg) Neo4j Generate CSVs

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Transform name | Neo4j Generate CSVs |  |
| Graph field | - | 此 Pipeline 中包含图形数据的字段（Graph 数据类型） |
| Base folder (below `import/` folder) | `/var/lib/neo4j` | 写入节点和关系类型的文件夹 |
| CSV files prefix | prefix | 应用于生成的 CSV 文件的前缀 |
| Node/Relationships Uniqueness strategy | None |  |
| Filename field | filename | 包含要生成的 CSV 文件文件名的字段 |
| File type field | fileType | 包含要生成的 CSV 文件类型的字段 |
