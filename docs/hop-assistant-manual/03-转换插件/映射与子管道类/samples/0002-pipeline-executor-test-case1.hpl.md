# Pipeline: 0002-test-case

## Basic Information

- **Pipeline Name:** 0002-test-case
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-pipeline-executor-test-case1.hpl`

## Transforms

| Name | Type |
|------|------|
| 5 rows | RowGenerator |
| nr | Sequence |
| repeating-work.hpl | PipelineExecutor |

## Hops

| From | To |
|------|----|
| 5 rows | nr |
| nr | repeating-work.hpl |
