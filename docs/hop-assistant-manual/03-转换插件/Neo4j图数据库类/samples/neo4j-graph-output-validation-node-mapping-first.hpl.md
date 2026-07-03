# Pipeline: neo4j-graph-output-validation-node-mapping-first

## Basic Information

- **Pipeline Name:** neo4j-graph-output-validation-node-mapping-first
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-validation-node-mapping-first.hpl`

## Transforms

| Name | Type |
|------|------|
| Read :One nodes | Neo4jCypherOutput |
| Row normaliser | Normaliser |
| Validation | Dummy |
| cleanup | SelectValues |

## Hops

| From | To |
|------|----|
| Read :One nodes | Row normaliser |
| Row normaliser | cleanup |
| cleanup | Validation |
