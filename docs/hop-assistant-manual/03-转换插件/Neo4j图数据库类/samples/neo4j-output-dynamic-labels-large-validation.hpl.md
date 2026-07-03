# Pipeline: neo4j-output-dynamic-labels-large-validation

## Basic Information

- **Pipeline Name:** neo4j-output-dynamic-labels-large-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-dynamic-labels-large-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Count labels | Neo4jCypherOutput |
| VALIDATE | Dummy |
| build cypher | ScriptValueMod |
| country,type data | DataGrid |
| cleanup | SelectValues |

## Hops

| From | To |
|------|----|
| build cypher | Count labels |
| country,type data | build cypher |
| Count labels | cleanup |
| cleanup | VALIDATE |
