# Pipeline: 0062-execute-process

## Basic Information

- **Pipeline Name:** 0062-execute-process
- **Source File:** `03-转换插件/网络与服务类/samples/0062-execute-process.hpl`

## Transforms

| Name | Type |
|------|------|
| Execute a process | ExecProcess |
| Verify | Dummy |
| sample-rows | DataGrid |

## Hops

| From | To |
|------|----|
| sample-rows | Execute a process |
| Execute a process | Verify |
