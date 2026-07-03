# Pipeline: 0023-flattener-parent

## Basic Information

- **Pipeline Name:** 0023-flattener-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0023-flattener-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0023-flattener-child.hpl | MetaInject |
| Verify | Dummy |
| field | DataGrid |
| targets | DataGrid |

## Hops

| From | To |
|------|----|
| field | 0023-flattener-child.hpl |
| targets | 0023-flattener-child.hpl |
| 0023-flattener-child.hpl | Verify |
