# Pipeline: neo4j-graph-output-validation-node-mapping-using-value

## Basic Information

- **Pipeline Name:** neo4j-graph-output-validation-node-mapping-using-value
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-validation-node-mapping-using-value.hpl`

## Transforms

| Name | Type |
|------|------|
| Validation | Dummy |
| Read :One nodes | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Read :One nodes | Validation |
