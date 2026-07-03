# Pipeline: neo4j-output-merge-nodes

## Basic Information

- **Pipeline Name:** neo4j-output-merge-nodes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-merge-nodes.hpl`

## Transforms

| Name | Type |
|------|------|
| [:Customer] | Neo4JOutput |
| customers-1k.txt | CsvInput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | [:Customer] |

## Notes

Commit size set to 123 to check for dangling records

---
