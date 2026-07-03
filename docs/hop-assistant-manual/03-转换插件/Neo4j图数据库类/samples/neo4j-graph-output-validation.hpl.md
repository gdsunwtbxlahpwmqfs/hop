# Pipeline: neo4j-cypher-graph-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-graph-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| CUSTOMERS | Dummy |
| Read :Customer | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Read :Customer | CUSTOMERS |
