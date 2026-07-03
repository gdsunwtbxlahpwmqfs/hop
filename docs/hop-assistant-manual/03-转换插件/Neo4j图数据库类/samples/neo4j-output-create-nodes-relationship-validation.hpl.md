# Pipeline: neo4j-output-create-nodes-relationship-validation

## Basic Information

- **Pipeline Name:** neo4j-output-create-nodes-relationship-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-create-nodes-relationship-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| MATCH(c:Customer)-[r:LIVES_IN]->(s:State) | Neo4jCypherOutput |
| OUTPUT | Dummy |

## Hops

| From | To |
|------|----|
| MATCH(c:Customer)-[r:LIVES_IN]->(s:State) | OUTPUT |
