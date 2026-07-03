# Pipeline: neo4j-gencsv-null-values-2950

## Basic Information

- **Pipeline Name:** neo4j-gencsv-null-values-2950
- **Description:** Test pipeline to reproduce issue #2950: Generate CSV errors on NULL values
- **Extended Description:** This pipeline tests the Generate CSV transform with NULL values in properties. The current implementation fails with NullPointerException when properties have NULL values. After the fix, this should complete successfully.
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-gencsv-null-values-2950.hpl`

## Transforms

| Name | Type |
|------|------|
| Create-Graph-Data | Neo4JOutput |
| Generate CSV | Neo4jLoad |
| Test Data with NULLs | DataGrid |

## Hops

| From | To |
|------|----|
| Test Data with NULLs | Create-Graph-Data |
| Create-Graph-Data | Generate CSV |
