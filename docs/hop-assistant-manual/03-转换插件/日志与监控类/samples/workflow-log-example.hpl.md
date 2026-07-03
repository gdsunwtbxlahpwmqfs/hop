# Pipeline: workflow-log-example

## Basic Information

- **Pipeline Name:** workflow-log-example
- **Source File:** `03-转换插件/日志与监控类/samples/workflow-log-example.hpl`

## Transforms

| Name | Type |
|------|------|
| Workflow Logging | WorkflowLogging |
| action logs | SelectValues |
| unique workflow data | SortRows |
| workflow logs | SelectValues |
| workflow-log-action.csv out | TextFileOutput |
| workflow-log.csv out | TextFileOutput |

## Hops

| From | To |
|------|----|
| workflow logs | unique workflow data |
| action logs | workflow-log-action.csv out |
| Workflow Logging | workflow logs |
| Workflow Logging | action logs |
| unique workflow data | workflow-log.csv out |
