# Pipeline: 0083-workflow-executor-run-wf

## Basic Information

- **Pipeline Name:** 0083-workflow-executor-run-wf
- **Source File:** `03-转换插件/映射与子管道类/samples/0083-workflow-executor-run-wf.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Add constants | Constant |
| Data grid | DataGrid |
| Filter rows | FilterRows |
| Group by | GroupBy |
| Metadata structure of stream | TransformMetaStructure |
| Movimenti_linee_credito_starter | WorkflowExecutor |
| Success | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Movimenti_linee_credito_starter |
| Movimenti_linee_credito_starter | Metadata structure of stream |
| Metadata structure of stream | Group by |
| Group by | Add constants |
| Add constants | Filter rows |
| Filter rows | Success |
| Filter rows | Abort |
