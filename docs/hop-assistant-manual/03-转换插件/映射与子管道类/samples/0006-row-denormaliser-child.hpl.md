# Pipeline: 0006-row-denormaliser-child

## Basic Information

- **Pipeline Name:** 0006-row-denormaliser-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0006-row-denormaliser-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Input data | DataGrid |
| Row denormaliser: colors | Denormaliser |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Input data | Row denormaliser: colors |
| Row denormaliser: colors | Verify |
