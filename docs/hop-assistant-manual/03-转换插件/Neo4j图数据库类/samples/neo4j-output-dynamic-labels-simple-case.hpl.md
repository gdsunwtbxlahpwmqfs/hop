# Pipeline: neo4j-output-dynamic-labels-simple-case

## Basic Information

- **Pipeline Name:** neo4j-output-dynamic-labels-simple-case
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-dynamic-labels-simple-case.hpl`

## Transforms

| Name | Type |
|------|------|
| countries | DataGrid |
| (:Country) | Neo4JOutput |
| label | Constant |

## Hops

| From | To |
|------|----|
| countries | label |
| label | (:Country) |
