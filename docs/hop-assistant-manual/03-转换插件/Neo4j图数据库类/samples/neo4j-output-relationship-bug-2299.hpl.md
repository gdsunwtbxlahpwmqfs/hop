# Pipeline: neo4j-output-relationship-bug-2299

## Basic Information

- **Pipeline Name:** neo4j-output-relationship-bug-2299
- **Description:** Test pipeline to reproduce issue #2299: incorrect relationships when relationship value changes in batch
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-relationship-bug-2299.hpl`

## Transforms

| Name | Type |
|------|------|
| Neo4j Output | Neo4JOutput |
| Test Data | DataGrid |

## Hops

| From | To |
|------|----|
| Test Data | Neo4j Output |
