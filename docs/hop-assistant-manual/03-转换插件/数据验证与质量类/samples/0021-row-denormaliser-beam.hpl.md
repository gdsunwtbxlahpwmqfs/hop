# Pipeline: 0021-row-denormaliser-beam

## Basic Information

- **Pipeline Name:** 0021-row-denormaliser-beam
- **Source File:** `03-转换插件/数据验证与质量类/samples/0021-row-denormaliser-beam.hpl`

## Transforms

| Name | Type |
|------|------|
| Input data | DataGrid |
| Row denormaliser: colors | Denormaliser |
| /tmp/0021-row-denormaliser-beam.csv | BeamOutput |
| aggregate results | MemoryGroupBy |

## Hops

| From | To |
|------|----|
| Input data | Row denormaliser: colors |
| Row denormaliser: colors | aggregate results |
| aggregate results | /tmp/0021-row-denormaliser-beam.csv |
