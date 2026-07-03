# Pipeline: 0005-read-from-remote-location

## Basic Information

- **Pipeline Name:** 0005-read-from-remote-location
- **Source File:** `03-转换插件/samples/0005-read-from-remote-location.hpl`

## Transforms

| Name | Type |
|------|------|
| Execution & State | ExecInfo |
| Execution IDs | ExecInfo |
| Limit fields | SelectValues |
| Output transform only | FilterRows |
| Remove XML and logging | SelectValues |
| Validate | Dummy |
| children, no limit | DataGrid |
| execution data | ExecInfo |
| executionType=Pipeline | FilterRows |

## Hops

| From | To |
|------|----|
| children, no limit | Execution IDs |
| execution data | Output transform only |
| Output transform only | Limit fields |
| Limit fields | Validate |
| Execution & State | executionType=Pipeline |
| executionType=Pipeline | Remove XML and logging |
| Remove XML and logging | execution data |
| Execution IDs | Execution & State |
