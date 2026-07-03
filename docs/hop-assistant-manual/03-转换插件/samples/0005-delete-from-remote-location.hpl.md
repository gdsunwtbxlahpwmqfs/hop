# Pipeline: 0005-delete-from-remote-location

## Basic Information

- **Pipeline Name:** 0005-delete-from-remote-location
- **Source File:** `03-转换插件/samples/0005-delete-from-remote-location.hpl`

## Transforms

| Name | Type |
|------|------|
| Execution & State | ExecInfo |
| Execution IDs | ExecInfo |
| Minimal only | SelectValues |
| children, no limit | DataGrid |
| delete executions | ExecInfo |
| executionType=Pipeline | FilterRows |
| remove id | SelectValues |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| Execution & State | executionType=Pipeline |
| Execution IDs | Execution & State |
| children, no limit | Execution IDs |
| executionType=Pipeline | Minimal only |
| Minimal only | delete executions |
| delete executions | remove id |
| remove id | validate |
