# Pipeline: neo4j-graph-output-validation-node-mapping-set-label

## Basic Information

- **Pipeline Name:** neo4j-graph-output-validation-node-mapping-set-label
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-graph-output-validation-node-mapping-set-label.hpl`

## Transforms

| Name | Type |
|------|------|
| Read :IPAddress nodes | Neo4jCypherOutput |
| Validation | Dummy |
| Read :IPAddress:Public nodes 2 | Neo4jCypherOutput |
| Sort by ip, labels | SortRows |

## Hops

| From | To |
|------|----|
| Read :IPAddress nodes | Sort by ip, labels |
| Sort by ip, labels | Validation |
| Read :IPAddress:Public nodes 2 | Sort by ip, labels |
