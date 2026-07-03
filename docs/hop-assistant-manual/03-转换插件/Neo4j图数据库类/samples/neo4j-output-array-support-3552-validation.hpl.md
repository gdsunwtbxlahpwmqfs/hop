# Pipeline: neo4j-output-array-support-3552-validation

## Basic Information

- **Pipeline Name:** neo4j-output-array-support-3552-validation
- **Description:** Validation pipeline for Array type support test
- **Extended Description:** Validates that Array type properties were correctly written to Neo4j nodes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-array-support-3552-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Neo4j Cypher | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Neo4j Cypher | Dummy (do nothing) |
