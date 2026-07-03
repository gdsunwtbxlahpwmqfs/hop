# Pipeline: neo4j-cypher-returns-validation

## Basic Information

- **Pipeline Name:** neo4j-cypher-returns-validation
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-cypher-returns-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| 1000 rows | RowGenerator |
| HOUSENRS | Dummy |
| Read :Customer | Neo4jCypherOutput |
| id | Sequence |

## Hops

| From | To |
|------|----|
| Read :Customer | HOUSENRS |
| 1000 rows | id |
| id | Read :Customer |

## Notes

Sets number of retries to verify a bug where a query was

read multiple times when "retries on error" was set (HOP-3146).

If a query works it shouldn't be retried.

---
