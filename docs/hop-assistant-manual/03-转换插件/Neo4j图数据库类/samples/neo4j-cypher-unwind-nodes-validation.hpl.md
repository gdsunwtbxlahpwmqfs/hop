# Pipeline: neo4j-cypher-unwind-nodes-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-unwind-nodes-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-unwind-nodes-validation.hpl`

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

With input and batch size > 1 this is also testing for trailing records in the input stream.

---
