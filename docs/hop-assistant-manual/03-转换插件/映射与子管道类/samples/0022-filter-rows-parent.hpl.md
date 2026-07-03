# Pipeline: 0022-filter-rows-parent

## Basic Information

- **Pipeline Name:** 0022-filter-rows-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0022-filter-rows-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0022-filter-rows-child.hpl | MetaInject |
| Verify | Dummy |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | 0022-filter-rows-child.hpl |
| 0022-filter-rows-child.hpl | Verify |
