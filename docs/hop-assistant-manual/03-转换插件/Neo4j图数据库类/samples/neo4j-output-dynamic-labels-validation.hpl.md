# Pipeline: neo4j-output-dynamic-labels-validation

## Basic Information

- **Pipeline Name:** neo4j-output-dynamic-labels-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-dynamic-labels-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Neo4j Cypher | Neo4jCypherOutput |
| Select values | SelectValues |
| VALIDATE | Dummy |
| build cypher | ScriptValueMod |
| countries | DataGrid |

## Hops

| From | To |
|------|----|
| countries | build cypher |
| build cypher | Neo4j Cypher |
| Neo4j Cypher | Select values |
| Select values | VALIDATE |
