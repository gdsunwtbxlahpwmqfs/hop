# Pipeline: neo4j-graph-output-validation-relationship-mapping-none

## Basic Information

- **Pipeline Name:** neo4j-graph-output-validation-relationship-mapping-none
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-validation-relationship-mapping-none.hpl`

## Transforms

| Name | Type |
|------|------|
| AB | Dummy |
| Read :A and :B | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Read :A and :B | AB |
