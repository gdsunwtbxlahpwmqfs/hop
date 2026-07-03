# Pipeline: 0003-add-sequence-parent

## Basic Information

- **Pipeline Name:** 0003-add-sequence-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0003-add-sequence-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Add Sequence metadata | DataGrid |
| 0003-add-sequence-child.hpl | MetaInject |
| Row Generator metadata | DataGrid |

## Hops

| From | To |
|------|----|
| Add Sequence metadata | 0003-add-sequence-child.hpl |
| Row Generator metadata | 0003-add-sequence-child.hpl |
