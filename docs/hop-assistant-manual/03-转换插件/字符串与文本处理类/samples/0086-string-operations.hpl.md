# Pipeline: 0086-string-operations

## Basic Information

- **Pipeline Name:** 0086-string-operations
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0086-string-operations.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| String operations | StringOperations |
| Validate | Dummy |
| Data grid 2 | DataGrid |
| String operations 2 | StringOperations |
| Validate 2 | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | String operations |
| String operations | Validate |
| Data grid 2 | String operations 2 |
| String operations 2 | Validate 2 |
