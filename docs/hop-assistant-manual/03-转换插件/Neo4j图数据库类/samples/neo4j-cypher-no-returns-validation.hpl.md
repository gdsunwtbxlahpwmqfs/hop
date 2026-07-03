# Pipeline: neo4j-cypher-no-returns-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-no-returns-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-no-returns-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| 1000 rows | RowGenerator |
| IDS | Dummy |
| Read :Customer | Neo4jCypherOutput |
| id | Sequence |

## Hops

| From | To |
|------|----|
| Read :Customer | IDS |
| 1000 rows | id |
| id | Read :Customer |
