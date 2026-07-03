# Pipeline: 0021-split-fields-parent

## Basic Information

- **Pipeline Name:** 0021-split-fields-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0021-split-fields-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0021-split-fields-child.hpl | MetaInject |
| Verify | Dummy |
| fields | DataGrid |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| fields | 0021-split-fields-child.hpl |
| 0021-split-fields-child.hpl | Verify |
| metadata | 0021-split-fields-child.hpl |
