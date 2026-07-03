# Pipeline: safe-stop

## Basic Information

- **Pipeline Name:** safe-stop
- **Source File:** `03-转换插件/输入类/samples/safe-stop.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate Rows | RowGenerator |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Generate Rows | Write to log |
