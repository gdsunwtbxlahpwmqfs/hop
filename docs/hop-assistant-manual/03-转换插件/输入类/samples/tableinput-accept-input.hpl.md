# Pipeline: tableinput-accept-input

## Basic Information

- **Pipeline Name:** tableinput-accept-input
- **Source File:** `03-转换插件/输入类/samples/tableinput-accept-input.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PRM_ID | 2 | this parameter is used in the where clause of the query in 'Table Input' |

## Transforms

| Name | Type |
|------|------|
| Table input With Input Data | TableInput |
| Output | Dummy |
| id = 2 | DataGrid |

## Hops

| From | To |
|------|----|
| Table input With Input Data | Output |
| id = 2 | Table input With Input Data |

## Notes

Read rows from a database query, accepting input data from a previous transform

---
