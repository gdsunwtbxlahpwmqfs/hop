# Pipeline: test1-workflow-get-variable

## Basic Information

- **Pipeline Name:** test1-workflow-get-variable
- **Source File:** `03-转换插件/计算与字段操作类/samples/0001-get-variable.hpl`

## Transforms

| Name | Type |
|------|------|
| 1 row | RowGenerator |
| Check variable value | WriteToLog |
| get ${VAR_TEST1} | GetVariable |
| Copy rows to result | RowsToResult |

## Hops

| From | To |
|------|----|
| 1 row | get ${VAR_TEST1} |
| get ${VAR_TEST1} | Check variable value |
| Check variable value | Copy rows to result |
