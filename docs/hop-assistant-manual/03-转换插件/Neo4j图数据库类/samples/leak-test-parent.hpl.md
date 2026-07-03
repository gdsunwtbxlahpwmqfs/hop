# Pipeline: leak-test-parent

## Basic Information

- **Pipeline Name:** leak-test-parent
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/leak-test-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 1000 rows | RowGenerator |
| leak-test-child.hpl | PipelineExecutor |

## Hops

| From | To |
|------|----|
| 1000 rows | leak-test-child.hpl |
