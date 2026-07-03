# Pipeline: 0002-pipeline-executor-grouping-onfield1

## Basic Information

- **Pipeline Name:** 0002-pipeline-executor-grouping-onfield1
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-pipeline-executor-grouping-onfield.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| 0002-pipeline-executor-gfield-checkrowsnum.hpl | PipelineExecutor |
| Data grid | DataGrid |
| Group by | GroupBy |
| Success | Dummy |
| Write to log | WriteToLog |
| tot = 2 | FilterRows |

## Hops

| From | To |
|------|----|
| Data grid | 0002-pipeline-executor-gfield-checkrowsnum.hpl |
| 0002-pipeline-executor-gfield-checkrowsnum.hpl | Group by |
| Group by | tot = 2 |
| tot = 2 | Abort |
| tot = 2 | Write to log |
| Write to log | Success |
