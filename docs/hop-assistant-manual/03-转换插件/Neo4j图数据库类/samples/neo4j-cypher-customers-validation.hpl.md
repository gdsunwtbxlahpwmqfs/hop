# Pipeline: neo4j-cypher-customers-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-customers-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-customers-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| 1 row | RowGenerator |
| CUSTOMERS | Dummy |
| Read :Customer | Neo4jCypherOutput |

## Hops

| From | To |
|------|----|
| Read :Customer | CUSTOMERS |
| 1 row | Read :Customer |

## Notes

With input and batch size > 1 this is also testing for loss of records at the end of the stream.

---
