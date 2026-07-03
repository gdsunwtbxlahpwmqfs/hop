# Pipeline: 0003-input-bigtable-readback

## Basic Information

- **Pipeline Name:** 0003-input-bigtable-readback
- **Source File:** `03-转换插件/云服务类/samples/0003-input-bigtable-readback.hpl`

## Transforms

| Name | Type |
|------|------|
| 0003-input-bigtable.csv | BeamOutput |
| apache-hop-it.customers | BeamBigtableInput |
| count by state | MemoryGroupBy |

## Hops

| From | To |
|------|----|
| apache-hop-it.customers | count by state |
| count by state | 0003-input-bigtable.csv |
