# Pipeline: 0011-run-test-execution-pipeline

## Basic Information

- **Pipeline Name:** 0011-run-test-execution-pipeline
- **Source File:** `03-转换插件/计算与字段操作类/samples/0011-run-test-execution-pipeline.hpl`

## Transforms

| Name | Type |
|------|------|
| 0011-called-workflow | WorkflowExecutor |
| Add sequence | Sequence |
| Generate rows | RowGenerator |
| Dummy (do nothing) | Dummy |
| Detect empty stream | DetectEmptyStream |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Generate rows | Add sequence |
| Add sequence | 0011-called-workflow |
| 0011-called-workflow | Dummy (do nothing) |
| Dummy (do nothing) | Detect empty stream |
| Detect empty stream | Abort |
