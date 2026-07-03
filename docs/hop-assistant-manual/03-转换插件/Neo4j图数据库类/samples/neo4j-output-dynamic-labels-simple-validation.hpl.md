# Pipeline: neo4j-output-dynamic-labels-simple-validation

## Basic Information

- **Pipeline Name:** neo4j-output-dynamic-labels-simple-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-dynamic-labels-simple-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| (:Country) | Neo4jCypherOutput |
| VALIDATE | Dummy |

## Hops

| From | To |
|------|----|
| (:Country) | VALIDATE |
