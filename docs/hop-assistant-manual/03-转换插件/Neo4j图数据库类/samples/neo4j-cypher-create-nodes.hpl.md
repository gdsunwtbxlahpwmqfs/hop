# Pipeline: neo4j-cypher-create-nodes

## Basic Information

- **Pipeline Name:** neo4j-cypher-create-nodes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-create-nodes.hpl`

## Transforms

| Name | Type |
|------|------|
| create :Customer | Neo4jCypherOutput |
| customers-1k.txt | CSVInput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | create :Customer |
