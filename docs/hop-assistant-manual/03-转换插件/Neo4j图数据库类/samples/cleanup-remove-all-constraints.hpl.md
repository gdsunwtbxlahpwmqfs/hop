# Pipeline: cleanup-remove-all-constraints

## Basic Information

- **Pipeline Name:** cleanup-remove-all-constraints
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/cleanup-remove-all-constraints.hpl`

## Transforms

| Name | Type |
|------|------|
| Build DROP CONSTRAINT cypher | ScriptValueMod |
| Get constraints list on database | Neo4jCypherOutput |
| perform DROP CONSTRAINT | Neo4jCypherOutput |
| remove " | ReplaceString |

## Hops

| From | To |
|------|----|
| Build DROP CONSTRAINT cypher | perform DROP CONSTRAINT |
| Get constraints list on database | remove " |
| remove " | Build DROP CONSTRAINT cypher |
