# Pipeline: neo4j-graph-output-build-node-mapping-first

## Basic Information

- **Pipeline Name:** neo4j-graph-output-build-node-mapping-first
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-build-node-mapping-first.hpl`

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

Merge to nodes explicitly selecting all node labels from the model

---
