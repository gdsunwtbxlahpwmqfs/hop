# Pipeline: 0006-row-denormaliser-parent

## Basic Information

- **Pipeline Name:** 0006-row-denormaliser-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0006-row-denormaliser-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0006-row-denormaliser-child.hpl | MetaInject |
| Verify | Dummy |
| group/key field | DataGrid |
| target fields | DataGrid |

## Hops

| From | To |
|------|----|
| 0006-row-denormaliser-child.hpl | Verify |
| target fields | 0006-row-denormaliser-child.hpl |
| group/key field | 0006-row-denormaliser-child.hpl |
