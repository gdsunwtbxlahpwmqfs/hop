# Pipeline: 0006-internal-pipeline-variables

## Basic Information

- **Pipeline Name:** 0006-internal-pipeline-variables
- **Source File:** `03-转换插件/计算与字段操作类/samples/0007-internal-pipeline-variables.hpl`

## Transforms

| Name | Type |
|------|------|
| fields | WriteToLog |
| internal.entry.current.folder | GetVariable |
| not resolved? | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| internal.entry.current.folder | fields |
| fields | not resolved? |
| not resolved? | Abort |
