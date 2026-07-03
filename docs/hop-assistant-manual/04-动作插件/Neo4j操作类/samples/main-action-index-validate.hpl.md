# Pipeline: main-action-index-validate

## Basic Information

- **Pipeline Name:** main-action-index-validate
- **Source File:** `04-动作插件/Neo4j操作类/samples/main-action-index-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Query indexes | Neo4jCypherOutput |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| Query indexes | Validate |
