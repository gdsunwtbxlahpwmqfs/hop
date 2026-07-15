# ![Neo4j Cypher transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/neo4j_cypher.svg) Neo4j Cypher

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 重要选项

| 选项 | 描述 |
|---|---|
| Get Cypher from input field | 可以不硬编码语句，而是从输入字段中获取（例如通过 JavaScript 生成）。 |
| Cypher | 要执行的 Cypher 语句。 |
| Use UNWIND to collect parameter values? | 此选项将所有参数收集到一个 map 中，将所有行的 map 添加到一个列表中，并将其传递给 UNWIND 语句。 |
| Name of UNWIND values map | 包含 UNWIND 语句所有行和值的 map 名称。 |
| Parameters | 可以选择任何 Neo4j 认可的参数名称（不含空格等），指定要使用的字段和要转换为的 Neo4j 数据类型。 |
| Returns | 此 Transform 可以返回一行或多行数据。 |
