# Pipeline: neo4j-output-array-support-3552

## Basic Information

- **Pipeline Name:** neo4j-output-array-support-3552
- **Description:** Test pipeline for Array type support in Neo4j Output transform (#3552)
- **Extended Description:** This pipeline tests the Array type support feature:
1. Numeric arrays (e.g., embeddings: "1.0,2.0,3.0")
2. String arrays (e.g., tags: "tag1,tag2,tag3")
3. Default separator (comma) and enclosure (empty)
4. Array properties on nodes

The pipeline creates test data with array strings and writes them to Neo4j as Array type properties.
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-array-support-3552.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Neo4j Output | Neo4JOutput |

## Hops

| From | To |
|------|----|
| Test Data | Neo4j Output |
