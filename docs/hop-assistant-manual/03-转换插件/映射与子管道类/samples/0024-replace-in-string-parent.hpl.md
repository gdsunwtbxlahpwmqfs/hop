# Pipeline: 0024-replace-in-string-parent

## Basic Information

- **Pipeline Name:** 0024-replace-in-string-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0024-replace-in-string-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0024-replace-in-string-child.hpl | MetaInject |
| Verify | Dummy |
| fields | DataGrid |

## Hops

| From | To |
|------|----|
| fields | 0024-replace-in-string-child.hpl |
| 0024-replace-in-string-child.hpl | Verify |
