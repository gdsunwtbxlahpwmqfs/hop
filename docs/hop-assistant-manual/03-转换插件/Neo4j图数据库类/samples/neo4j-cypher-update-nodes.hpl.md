# Pipeline: neo4j-cypher-update-nodes

## Basic Information

- **Pipeline Name:** neo4j-cypher-update-nodes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-update-nodes.hpl`

## Transforms

| Name | Type |
|------|------|
| MATCH/SET :Customer | Neo4jCypherOutput |
| MATCH/SET :State | Neo4jCypherOutput |
| StateCode, State | UniqueRowsByHashSet |
| customers-1k.txt | CsvInput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | MATCH/SET :Customer |
| customers-1k.txt | StateCode, State |
| StateCode, State | MATCH/SET :State |

## Notes

Commit size set to 123 to check for dangling records

---
