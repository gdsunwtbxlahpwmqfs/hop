# Pipeline: neo4j-output-dynamic-labels-load-data

## Basic Information

- **Pipeline Name:** neo4j-output-dynamic-labels-load-data
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-dynamic-labels-large-load-data.hpl`

## Transforms

| Name | Type |
|------|------|
| Add sequence | Sequence |
| Clone row | CloneRow |
| Data grid | DataGrid |
| Neo4J Output | Neo4JOutput |

## Hops

| From | To |
|------|----|
| Add sequence | Neo4J Output |
| Clone row | Add sequence |
| Data grid | Clone row |

## Notes

### Note 1

99 clones: correct output

999 clones: incorrect output: old labels are used instead of new ones.

---

### Note 2

Generate data:

:DE:Adult (2x)

:DE:Child (1x)

:DE:Baby (1x)

:FR:Adult (2x)

:FR:Child (1x)

:FR:Baby (1x)

---
