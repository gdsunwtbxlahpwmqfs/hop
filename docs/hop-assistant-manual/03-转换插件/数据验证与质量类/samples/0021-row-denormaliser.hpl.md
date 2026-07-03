# Pipeline: 0021-row-denormaliser

## Basic Information

- **Pipeline Name:** 0021-row-denormaliser
- **Source File:** `03-转换插件/数据验证与质量类/samples/0021-row-denormaliser.hpl`

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
