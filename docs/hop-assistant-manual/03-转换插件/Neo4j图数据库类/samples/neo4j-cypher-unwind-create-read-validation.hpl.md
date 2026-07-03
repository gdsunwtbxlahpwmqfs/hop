# Pipeline: neo4j-cypher-unwind-create-read-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-unwind-create-read-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-unwind-create-read-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| create :Customer2 | Neo4jCypherOutput |
| customers-1k.txt | CSVInput |
| rename | SelectValues |

## Hops

| From | To |
|------|----|
| customers-1k.txt | create :Customer2 |
| create :Customer2 | rename |

## Notes

Commit size set to 123 to check for dangling records

---
