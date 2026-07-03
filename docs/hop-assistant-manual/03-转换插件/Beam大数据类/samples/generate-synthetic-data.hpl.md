# Pipeline: generate-synthetic-data

## Basic Information

- **Pipeline Name:** generate-synthetic-data
- **Source File:** `03-转换插件/Beam大数据类/samples/generate-synthetic-data.hpl`

## Transforms

| Name | Type |
|------|------|
| 100M rows | RowGenerator |
| generate-synthetic-data | BeamOutput |
| random data | RandomValue |

## Hops

| From | To |
|------|----|
| 100M rows | random data |
| random data | generate-synthetic-data |
