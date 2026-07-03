# Pipeline: drop all constraints

## Basic Information

- **Pipeline Name:** drop all constraints
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/drop all constraints.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| BATCH_SIZE |  |  |

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
| Get constraints list on database | remove " |
| remove " | Build DROP CONSTRAINT cypher |
| Build DROP CONSTRAINT cypher | perform DROP CONSTRAINT |
