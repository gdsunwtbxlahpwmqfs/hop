# Pipeline: write-to-bigtable

## Basic Information

- **Pipeline Name:** write-to-bigtable
- **Source File:** `03-转换插件/Beam大数据类/samples/write-to-bigtable.hpl`

## Transforms

| Name | Type |
|------|------|
| Beam Bigtable Output | BeamBigtableOutput |
| Neo4j Cypher | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Neo4j Cypher | Beam Bigtable Output |
