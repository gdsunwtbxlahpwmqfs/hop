# Pipeline: 0011-simple-mapping

## Basic Information

- **Pipeline Name:** 0011-simple-mapping
- **Source File:** `03-转换插件/Beam大数据类/samples/0011-simple-mapping.hpl`

## Transforms

| Name | Type |
|------|------|
| 0011-simple-mapping | BeamOutput |
| Simple Mapping | SimpleMapping |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| sample data | Simple Mapping |
| Simple Mapping | 0011-simple-mapping |
