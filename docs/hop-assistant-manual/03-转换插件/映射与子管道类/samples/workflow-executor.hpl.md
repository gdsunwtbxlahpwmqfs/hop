# Pipeline: workflow-executor

## Basic Information

- **Pipeline Name:** workflow-executor
- **Source File:** `03-转换插件/映射与子管道类/samples/workflow-executor.hpl`

## Transforms

| Name | Type |
|------|------|
| add counter | Sequence |
| execution results | Dummy |
| generate rows | RowGenerator |
| main output | Dummy |
| result file names after execution | Dummy |
| result rows after execution | Dummy |
| child-workflow-executor.hwf | WorkflowExecutor |

## Hops

| From | To |
|------|----|
| generate rows | add counter |
| add counter | child-workflow-executor.hwf |
| child-workflow-executor.hwf | execution results |
| child-workflow-executor.hwf | result rows after execution |
| child-workflow-executor.hwf | result file names after execution |
| child-workflow-executor.hwf | main output |
