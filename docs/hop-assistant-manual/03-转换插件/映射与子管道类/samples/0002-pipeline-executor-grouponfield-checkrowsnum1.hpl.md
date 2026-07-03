# Pipeline: 0002-pipeline-executor-grouponfield-checkrowsnum

## Basic Information

- **Pipeline Name:** 0002-pipeline-executor-grouponfield-checkrowsnum
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-pipeline-executor-grouponfield-checkrowsnum1.hpl`

## Transforms

| Name | Type |
|------|------|
| 0002-pipeline-executor-grouponfield-checkrowsnum-called.hpl | PipelineExecutor |
| Data grid | DataGrid |
| Filter rows | FilterRows |
| Dummy (do nothing) | Dummy |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Data grid | 0002-pipeline-executor-grouponfield-checkrowsnum-called.hpl |
| 0002-pipeline-executor-grouponfield-checkrowsnum-called.hpl | Filter rows |
| Filter rows | Dummy (do nothing) |
| Filter rows | Abort |
