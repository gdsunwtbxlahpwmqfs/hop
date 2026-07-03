# Pipeline: 0024-get-table-names-parent

## Basic Information

- **Pipeline Name:** 0024-get-table-names-parent
- **Source File:** `03-转换插件/数据库操作类/samples/0024-get-table-names-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0024-get-table-names-child.hpl | MetaInject |
| Verify | Dummy |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | 0024-get-table-names-child.hpl |
| 0024-get-table-names-child.hpl | Verify |
