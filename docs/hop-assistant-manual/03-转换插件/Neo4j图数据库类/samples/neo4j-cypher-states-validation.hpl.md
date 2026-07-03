# Pipeline: neo4j-cypher-states-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-states-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-states-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Read :Sate | Neo4jCypherOutput |
| STATES | Dummy |

## Hops

| From | To |
|------|----|
| Read :Sate | STATES |
