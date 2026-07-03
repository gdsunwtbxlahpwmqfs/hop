# Pipeline: neo4j-cypher-complex-returns

## Basic Information

- **Pipeline Name:** neo4j-cypher-complex-returns
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-complex-returns.hpl`

## Transforms

| Name | Type |
|------|------|
| Cypher query returning Node, Map and List | Neo4jCypherOutput |
| Process further with JSON Input | Dummy |

## Hops

| From | To |
|------|----|
| Cypher query returning Node, Map and List | Process further with JSON Input |

## Notes

Please run sample : neo4j-output-parallel-load first to see actual data returned.

---
