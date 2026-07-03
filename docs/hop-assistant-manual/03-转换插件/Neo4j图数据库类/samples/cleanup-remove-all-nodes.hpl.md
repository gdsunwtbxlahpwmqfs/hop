# Pipeline: cleanup-remove-all-nodes

## Basic Information

- **Pipeline Name:** cleanup-remove-all-nodes
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/cleanup-remove-all-nodes.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| BATCH_SIZE | 1000 | The number of nodes to delete at once |

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
| batchSize | build cypher |
| build cypher | totalCount |
| nrBatches | x nrBatches |
| totalCount | totalCount>0? |
| totalCount>0? | nrBatches |
| x nrBatches | DELETE |
