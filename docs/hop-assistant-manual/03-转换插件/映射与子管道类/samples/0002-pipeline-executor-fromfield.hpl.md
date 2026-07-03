# Pipeline: 0002-pipeline-executor-fromfield

## Basic Information

- **Pipeline Name:** 0002-pipeline-executor-fromfield
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-pipeline-executor-fromfield.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Call processor | PipelineExecutor |
| Data grid | DataGrid |
| Group by | GroupBy |
| Success | Dummy |
| tot = 2 | FilterRows |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Data grid | Call processor |
| Call processor | Group by |
| Group by | tot = 2 |
| tot = 2 | Abort |
| tot = 2 | Write to log |
| Write to log | Success |
