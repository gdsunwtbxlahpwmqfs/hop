# Pipeline: remove all relationships

## Basic Information

- **Pipeline Name:** remove all relationships
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/remove all relationships.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| BATCH_SIZE | 100000 | Size of block to delete |

## Transforms

| Name | Type |
|------|------|
| DELETE | Neo4jCypherOutput |
| batchSize | GetVariable |
| build cypher | ScriptValueMod |
| nrBatches | ScriptValueMod |
| totalCount | Neo4jCypherOutput |
| totalCount>0? | FilterRows |
| x nrBatches | CloneRow |

## Hops

| From | To |
|------|----|
| build cypher | totalCount |
| batchSize | build cypher |
| totalCount | totalCount>0? |
| nrBatches | x nrBatches |
| totalCount>0? | nrBatches |
| x nrBatches | DELETE |

## Notes

Delete all relationships in batches

---
