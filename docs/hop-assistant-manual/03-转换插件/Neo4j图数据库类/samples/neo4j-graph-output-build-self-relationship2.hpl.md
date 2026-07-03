# Pipeline: neo4j-graph-output-build-self-relationship2

## Basic Information

- **Pipeline Name:** neo4j-graph-output-build-self-relationship2
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-build-self-relationship2.hpl`

## Transforms

| Name | Type |
|------|------|
| Self2 | Neo4jGraphOutput |
| sample-data | DataGrid |

## Hops

| From | To |
|------|----|
| sample-data | Self2 |

## Notes

Create relationships between nodes with the same label.

We test asynchronous relationships:

person A considers person B a friend but not vice-versa.

---
