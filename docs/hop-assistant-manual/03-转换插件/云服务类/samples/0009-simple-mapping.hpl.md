# Pipeline: 0009-simple-mapping

## Basic Information

- **Pipeline Name:** 0009-simple-mapping
- **Source File:** `03-转换插件/云服务类/samples/0009-simple-mapping.hpl`

## Transforms

| Name | Type |
|------|------|
| 0009-simple-mapping | BeamOutput |
| hop/0009-simple-mapping-child.hpl | SimpleMapping |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| hop/0009-simple-mapping-child.hpl | 0009-simple-mapping |
| sample data | hop/0009-simple-mapping-child.hpl |
