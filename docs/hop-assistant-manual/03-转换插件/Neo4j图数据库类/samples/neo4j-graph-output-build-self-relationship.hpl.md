# Pipeline: neo4j-graph-output-build-self-relationship

## Basic Information

- **Pipeline Name:** neo4j-graph-output-build-self-relationship
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-build-self-relationship.hpl`

## Transforms

| Name | Type |
|------|------|
| sample-data | DataGrid |
| Self | Neo4jGraphOutput |

## Hops

| From | To |
|------|----|
| sample-data | Self |

## Notes

Create relationships between nodes with the same label.

We need to indicate which is the source and target in the field mapping.

We can also select which relationship to use or no relationship at all.

---
