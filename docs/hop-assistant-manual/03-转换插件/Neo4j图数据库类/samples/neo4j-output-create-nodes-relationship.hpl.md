# Pipeline: neo4j-output-create-nodes-relationship

## Basic Information

- **Pipeline Name:** neo4j-output-create-nodes-relationship
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-create-nodes-relationship.hpl`

## Transforms

| Name | Type |
|------|------|
| (:Customer)-[:LIVES_IN]->(:State) | Neo4JOutput |
| customers-1k.txt | CsvInput |
| labels | Constant |

## Hops

| From | To |
|------|----|
| customers-1k.txt | labels |
| labels | (:Customer)-[:LIVES_IN]->(:State) |

## Notes

Commit size set to 123 to check for dangling records

---
