# Pipeline: run-all-tests

## Basic Information

- **Pipeline Name:** run-all-tests
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/run-all-tests.hpl`

## Transforms

| Name | Type |
|------|------|
| Error detected | Abort |
| Error? | FilterRows |
| Get main workflows | GetFileNames |
| run-main-workflow.hwf | WorkflowExecutor |

## Hops

| From | To |
|------|----|
| Get main workflows | run-main-workflow.hwf |
| run-main-workflow.hwf | Error? |
| Error? | Error detected |
