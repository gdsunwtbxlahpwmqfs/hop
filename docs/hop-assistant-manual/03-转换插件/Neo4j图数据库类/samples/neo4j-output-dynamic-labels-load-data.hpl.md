# Pipeline: neo4j-output-dynamic-labels-load-data

## Basic Information

- **Pipeline Name:** neo4j-output-dynamic-labels-load-data
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-dynamic-labels-load-data.hpl`

## Transforms

| Name | Type |
|------|------|
| countries | DataGrid |
| countries out | Neo4JOutput |

## Hops

| From | To |
|------|----|
| countries | countries out |
