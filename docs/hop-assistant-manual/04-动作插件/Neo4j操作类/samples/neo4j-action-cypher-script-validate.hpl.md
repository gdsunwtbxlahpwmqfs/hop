# Pipeline: neo4j-action-cypher-script-validate

## Basic Information

- **Pipeline Name:** neo4j-action-cypher-script-validate
- **Source File:** `04-动作插件/Neo4j操作类/samples/neo4j-action-cypher-script-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| read (p:Project) | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| read (p:Project) | Verify |
