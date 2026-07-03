# Pipeline: neo4j-graph-output-build

## Basic Information

- **Pipeline Name:** neo4j-graph-output-build
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-build.hpl`

## Transforms

| Name | Type |
|------|------|
| Update Customers graph | Neo4jGraphOutput |
| customers-1k.txt | CSVInput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | Update Customers graph |

## Notes

Commit size set to 123 to check for dangling records

---
