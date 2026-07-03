# Pipeline: run-all

## Basic Information

- **Pipeline Name:** run-all
- **Source File:** `03-转换插件/数据库操作类/samples/run-all.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Filter Errors | FilterRows |
| get main workflows | GetFileNames |
| run-workflow.hwf | WorkflowExecutor |

## Hops

| From | To |
|------|----|
| Filter Errors | Abort |
| get main workflows | run-workflow.hwf |
| run-workflow.hwf | Filter Errors |
