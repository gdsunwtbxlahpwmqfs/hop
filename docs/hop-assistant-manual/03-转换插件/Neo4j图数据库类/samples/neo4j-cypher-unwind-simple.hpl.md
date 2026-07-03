# Pipeline: neo4j-cypher-unwind-simple

## Basic Information

- **Pipeline Name:** neo4j-cypher-unwind-simple
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-unwind-simple.hpl`

## Transforms

| Name | Type |
|------|------|
| Merge (:Year)-[:IN]-(:Event) | Neo4jCypherOutput |
| Sample data | DataGrid |

## Hops

| From | To |
|------|----|
| Sample data | Merge (:Year)-[:IN]-(:Event) |
