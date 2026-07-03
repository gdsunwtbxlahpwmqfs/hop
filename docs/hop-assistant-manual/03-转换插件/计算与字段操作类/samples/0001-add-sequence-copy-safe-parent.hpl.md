# Pipeline: 0001-add-sequence-copy-safe-parent

## Basic Information

- **Pipeline Name:** 0001-add-sequence-copy-safe-parent
- **Source File:** `03-转换插件/计算与字段操作类/samples/0001-add-sequence-copy-safe-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0001-add-sequence-copy-safe-child.hpl | PipelineExecutor |
| Data grid | DataGrid |

## Hops

| From | To |
|------|----|
| Data grid | 0001-add-sequence-copy-safe-child.hpl |
