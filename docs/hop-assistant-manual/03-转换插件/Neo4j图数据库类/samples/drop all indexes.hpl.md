# Pipeline: drop all indexes

## Basic Information

- **Pipeline Name:** drop all indexes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/drop all indexes.hpl`

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
| Get index list on database | remove " |
| remove " | Build DROP INDEX cypher |
| Build DROP INDEX cypher | perform DROP INDEX |
