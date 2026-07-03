# Pipeline: leak-test-child

## Basic Information

- **Pipeline Name:** leak-test-child
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/leak-test-child.hpl`

## Transforms

| Name | Type |
|------|------|
| (:Test) | Neo4JOutput |
| RETURN 1 | Neo4jCypherOutput |
| Test.one | Neo4jGraphOutput |

## Hops

| From | To |
|------|----|
| (:Test) | Test.one |
| RETURN 1 | (:Test) |
