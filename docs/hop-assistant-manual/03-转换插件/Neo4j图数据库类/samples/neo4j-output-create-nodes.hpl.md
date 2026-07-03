# Pipeline: neo4j-output-create-nodes

## Basic Information

- **Pipeline Name:** neo4j-output-create-nodes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-create-nodes.hpl`

## Transforms

| Name | Type |
|------|------|
| [:Customer] | Neo4JOutput |
| customers-1k.txt | CSVInput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | [:Customer] |

## Notes

Commit size set to 123 to check for dangling records

---
