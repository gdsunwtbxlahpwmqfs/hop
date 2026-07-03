# Pipeline: 0005-verify-deleted-on-remote-location

## Basic Information

- **Pipeline Name:** 0005-verify-deleted-on-remote-location
- **Source File:** `03-转换插件/samples/0005-verify-deleted-on-remote-location.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort if executions found | Abort |
| Execution IDs | ExecInfo |
| children, no limit | DataGrid |

## Hops

| From | To |
|------|----|
| children, no limit | Execution IDs |
| Execution IDs | Abort if executions found |
