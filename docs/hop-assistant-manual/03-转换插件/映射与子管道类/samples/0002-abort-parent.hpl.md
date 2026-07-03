# Pipeline: 0002-abort-parent

## Basic Information

- **Pipeline Name:** 0002-abort-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-abort-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort metadata | DataGrid |
| 0002-abort-child.hpl | MetaInject |
| Row Generator metadata | DataGrid |

## Hops

| From | To |
|------|----|
| Abort metadata | 0002-abort-child.hpl |
| Row Generator metadata | 0002-abort-child.hpl |
