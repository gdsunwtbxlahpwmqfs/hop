# Pipeline: rename labels in blocks

## Basic Information

- **Pipeline Name:** rename labels in blocks
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/rename labels in blocks.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| COPYNR | 0 | The copy nr |

## Transforms

| Name | Type |
|------|------|
| copyNr | GetVariable |
| Copy | Neo4jCypherOutput |
| build cypher | ScriptValueMod |
| oo | RowGenerator |
| done? | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| build cypher | Copy |
| copyNr | build cypher |
| oo | copyNr |
| Copy | done? |
| done? | Abort |
