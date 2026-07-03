# Pipeline: unbounded-synthetic-data

## Basic Information

- **Pipeline Name:** unbounded-synthetic-data
- **Source File:** `03-转换插件/Beam大数据类/samples/unbounded-synthetic-data.hpl`

## Transforms

| Name | Type |
|------|------|
| 60s window | BeamWindow |
| oo rows | RowGenerator |
| random data | RandomValue |
| unbounded-synthetic-data | BeamOutput |

## Hops

| From | To |
|------|----|
| oo rows | random data |
| random data | 60s window |
| 60s window | unbounded-synthetic-data |
