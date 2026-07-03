# Pipeline: 0002-pipeline-executor-grouponfield-checkrowsnum2

## Basic Information

- **Pipeline Name:** 0002-pipeline-executor-grouponfield-checkrowsnum2
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-pipeline-executor-grouponfield-checkrowsnum2.hpl`

## Transforms

| Name | Type |
|------|------|
| 0002-pipeline-executor-grouponfield-checkrowsnum-called.hpl | PipelineExecutor |
| Abort | Abort |
| Data grid | DataGrid |
| Dummy (do nothing) | Dummy |
| Filter rows | FilterRows |

## Hops

| From | To |
|------|----|
| Data grid | 0002-pipeline-executor-grouponfield-checkrowsnum-called.hpl |
| 0002-pipeline-executor-grouponfield-checkrowsnum-called.hpl | Filter rows |
| Filter rows | Dummy (do nothing) |
| Filter rows | Abort |
