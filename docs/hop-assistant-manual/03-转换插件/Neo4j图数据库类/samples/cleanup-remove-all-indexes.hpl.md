# Pipeline: cleanup-remove-all-indexes

## Basic Information

- **Pipeline Name:** cleanup-remove-all-indexes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/cleanup-remove-all-indexes.hpl`

## Transforms

| Name | Type |
|------|------|
| Build DROP INDEX cypher | ScriptValueMod |
| Get index list on database | Neo4jCypherOutput |
| perform DROP INDEX | Neo4jCypherOutput |
| remove " | ReplaceString |

## Hops

| From | To |
|------|----|
| Build DROP INDEX cypher | perform DROP INDEX |
| Get index list on database | remove " |
| remove " | Build DROP INDEX cypher |
