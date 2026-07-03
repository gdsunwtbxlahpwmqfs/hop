# Pipeline: mapping-use

## Basic Information

- **Pipeline Name:** mapping-use
- **Source File:** `03-转换插件/映射与子管道类/samples/simple-mapping-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Results | Dummy |
| Simple Mapping | SimpleMapping |
| id | Sequence |

## Hops

| From | To |
|------|----|
| Data grid | id |
| Simple Mapping | Results |
| id | Simple Mapping |
