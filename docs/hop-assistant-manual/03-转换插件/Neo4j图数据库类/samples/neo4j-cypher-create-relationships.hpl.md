# Pipeline: neo4j-output-create-nodes

## Basic Information

- **Pipeline Name:** neo4j-output-create-nodes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-create-relationships.hpl`

## Transforms

| Name | Type |
|------|------|
| MERGE (:Customer)-[:LIVES_IN]-(:State) | Neo4jCypherOutput |
| customers-1k.txt | CsvInput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | MERGE (:Customer)-[:LIVES_IN]-(:State) |

## Notes

Commit size set to 123 to check for dangling records

---
