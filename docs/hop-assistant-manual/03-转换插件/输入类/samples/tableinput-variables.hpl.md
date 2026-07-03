# Pipeline: tableinput-variables

## Basic Information

- **Pipeline Name:** tableinput-variables
- **Source File:** `03-转换插件/输入类/samples/tableinput-variables.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PRM_ID | 2 | this parameter is used in the where clause of the query in 'Table Input' |

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Table input With Var | TableInput |

## Hops

| From | To |
|------|----|
| Table input With Var | Output |

## Notes

Reads rows from a database query, using a variable (pipeline parameter PRM_ID) as part of the SQL query

---
