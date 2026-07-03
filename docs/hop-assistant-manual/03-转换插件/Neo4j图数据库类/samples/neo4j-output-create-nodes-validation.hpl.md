# Pipeline: neo4j-output-create-nodes-validation

## Basic Information

- **Pipeline Name:** neo4j-output-create-nodes-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-create-nodes-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Neo4j Cypher | Neo4jCypherOutput |
| VALIDATE | Dummy |

## Hops

| From | To |
|------|----|
| Neo4j Cypher | VALIDATE |
