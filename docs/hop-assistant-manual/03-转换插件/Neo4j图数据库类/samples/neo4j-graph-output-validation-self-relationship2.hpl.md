# Pipeline: neo4j-graph-output-validation-self-relationship2

## Basic Information

- **Pipeline Name:** neo4j-graph-output-validation-self-relationship2
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-validation-self-relationship2.hpl`

## Transforms

| Name | Type |
|------|------|
| Read :One nodes | Neo4jCypherOutput |
| Validation | Dummy |

## Hops

| From | To |
|------|----|
| Read :One nodes | Validation |
