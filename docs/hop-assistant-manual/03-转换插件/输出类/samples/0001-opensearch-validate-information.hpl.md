# Pipeline: 0001-opensearch-validate-information

## Basic Information

- **Pipeline Name:** 0001-opensearch-validate-information
- **Source File:** `03-转换插件/输出类/samples/0001-opensearch-validate-information.hpl`

## Transforms

| Name | Type |
|------|------|
| Execution & State | ExecInfo |
| Execution IDs | ExecInfo |
| Validate | Dummy |
| We have data | FilterRows |
| children, limit | DataGrid |
| limit fields | SelectValues |

## Hops

| From | To |
|------|----|
| children, limit | Execution IDs |
| Execution IDs | Execution & State |
| Execution & State | We have data |
| We have data | limit fields |
| limit fields | Validate |
