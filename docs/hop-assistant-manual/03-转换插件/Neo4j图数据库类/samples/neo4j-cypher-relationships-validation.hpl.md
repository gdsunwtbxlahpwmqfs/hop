# Pipeline: neo4j-cypher-relationships-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-relationships-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-relationships-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| CUSTOMERS | Dummy |
| Read :Customer | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Read :Customer | CUSTOMERS |
