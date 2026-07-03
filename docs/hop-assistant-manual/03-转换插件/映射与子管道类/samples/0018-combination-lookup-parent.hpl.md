# Pipeline: 0018-combination-lookup-parent

## Basic Information

- **Pipeline Name:** 0018-combination-lookup-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0018-combination-lookup-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0018-combination-lookup-child.hpl | MetaInject |
| fields | DataGrid |
| junk_dim | DataGrid |

## Hops

| From | To |
|------|----|
| junk_dim | 0018-combination-lookup-child.hpl |
| fields | 0018-combination-lookup-child.hpl |
