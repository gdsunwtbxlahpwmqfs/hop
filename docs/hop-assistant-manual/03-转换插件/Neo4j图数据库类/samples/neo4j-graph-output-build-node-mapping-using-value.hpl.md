# Pipeline: neo4j-graph-output-build-node-mapping-using-value

## Basic Information

- **Pipeline Name:** neo4j-graph-output-build-node-mapping-using-value
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-build-node-mapping-using-value.hpl`

## Transforms

| Name | Type |
|------|------|
| NodeLabels | Neo4jGraphOutput |
| sample-data | DataGrid |

## Hops

| From | To |
|------|----|
| sample-data | NodeLabels |

## Notes

Merge to nodes with one of 3 labels depending on the input data.

---
