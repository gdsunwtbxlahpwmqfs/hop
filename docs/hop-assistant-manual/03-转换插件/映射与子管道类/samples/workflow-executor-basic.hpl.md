# Pipeline: workflow-executor-basic

## Basic Information

- **Pipeline Name:** workflow-executor-basic
- **Source File:** `03-转换插件/映射与子管道类/samples/workflow-executor-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Result files | Dummy |
| Results | Dummy |
| Data for parameters | DataGrid |
| workflow-executor-child.hwf | WorkflowExecutor |

## Hops

| From | To |
|------|----|
| Data for parameters | workflow-executor-child.hwf |
| workflow-executor-child.hwf | Result files |
| workflow-executor-child.hwf | Results |

## Notes

This pipeline execute a workflow once for every input row.

It passes values to a parameter that is set as a variable in the workflow.

---
