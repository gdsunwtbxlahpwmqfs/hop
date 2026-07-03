# Pipeline: pipeline-executor

## Basic Information

- **Pipeline Name:** pipeline-executor
- **Source File:** `03-转换插件/映射与子管道类/samples/pipeline-executor.hpl`

## Transforms

| Name | Type |
|------|------|
| add counter | Sequence |
| copy of input rows | Dummy |
| execution results | Dummy |
| generate rows | RowGenerator |
| loops-log-counter.hpl | PipelineExecutor |
| main output | Dummy |
| result file names after execution | Dummy |
| result rows after execution | Dummy |

## Hops

| From | To |
|------|----|
| generate rows | add counter |
| add counter | loops-log-counter.hpl |
| loops-log-counter.hpl | execution results |
| loops-log-counter.hpl | result rows after execution |
| loops-log-counter.hpl | result file names after execution |
| loops-log-counter.hpl | copy of input rows |
| loops-log-counter.hpl | main output |
