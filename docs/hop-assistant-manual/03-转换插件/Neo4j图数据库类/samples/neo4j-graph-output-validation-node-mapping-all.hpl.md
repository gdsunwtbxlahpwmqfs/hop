# Pipeline: neo4j-graph-output-validation-node-mapping-all

## Basic Information

- **Pipeline Name:** neo4j-graph-output-validation-node-mapping-all
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-validation-node-mapping-all.hpl`

## Transforms

| Name | Type |
|------|------|
| Read :One nodes | Neo4jCypherOutput |
| Validation | Dummy |
| Row normaliser | Normaliser |
| cleanup | SelectValues |

## Hops

| From | To |
|------|----|
| Read :One nodes | Row normaliser |
| Row normaliser | cleanup |
| cleanup | Validation |
