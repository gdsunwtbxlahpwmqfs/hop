# Pipeline: neo4j-graph-output-build-relationship-mapping-none

## Basic Information

- **Pipeline Name:** neo4j-graph-output-build-relationship-mapping-none
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-build-relationship-mapping-none.hpl`

## Transforms

| Name | Type |
|------|------|
| AB | Neo4jGraphOutput |
| sample-data | DataGrid |

## Hops

| From | To |
|------|----|
| sample-data | AB |

## Notes

Do not create any relationships between nodes A and B.

Only merge A and B nodes

---
