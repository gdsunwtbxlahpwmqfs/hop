# Pipeline: neo4j-graph-output-build-relationship-mapping-all

## Basic Information

- **Pipeline Name:** neo4j-graph-output-build-relationship-mapping-all
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-build-relationship-mapping-all.hpl`

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

Create ALL defined relationships between nodes A and B.

Update the type property on all these relationships

---
