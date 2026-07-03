# Pipeline: neo4j-action-constraint-validate

## Basic Information

- **Pipeline Name:** neo4j-action-constraint-validate
- **Source File:** `04-动作插件/Neo4j操作类/samples/neo4j-action-constraint-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Query constraints | Neo4jCypherOutput |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| Query constraints | Validate |
