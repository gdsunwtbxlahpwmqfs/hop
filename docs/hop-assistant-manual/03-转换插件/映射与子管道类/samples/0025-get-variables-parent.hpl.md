# Pipeline: 0025-get-variables-parent

## Basic Information

- **Pipeline Name:** 0025-get-variables-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0025-get-variables-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0025-get-variables-child.hpl | MetaInject |
| Verify | Dummy |
| variable fields | DataGrid |

## Hops

| From | To |
|------|----|
| variable fields | 0025-get-variables-child.hpl |
| 0025-get-variables-child.hpl | Verify |
