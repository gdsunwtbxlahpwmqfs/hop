# Pipeline: 2812-check-graph

## Basic Information

- **Pipeline Name:** 2812-check-graph
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/2812-check-graph.hpl`

## Transforms

| Name | Type |
|------|------|
| read graph | Neo4jCypherOutput |
| check results | FilterRows |
| Ok | Dummy |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| read graph | check results |
| check results | Ok |
| check results | Abort |
