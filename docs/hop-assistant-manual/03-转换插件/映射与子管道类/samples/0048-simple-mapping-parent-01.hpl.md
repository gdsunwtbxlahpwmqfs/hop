# Pipeline: 0048-simple-mapping-parent-01

## Basic Information

- **Pipeline Name:** 0048-simple-mapping-parent-01
- **Source File:** `03-转换插件/映射与子管道类/samples/0048-simple-mapping-parent-01.hpl`

## Transforms

| Name | Type |
|------|------|
| 0048-simple-mapping-child-01.hpl | SimpleMapping |
| Output | Dummy |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| 0048-simple-mapping-child-01.hpl | Output |
| sample data | 0048-simple-mapping-child-01.hpl |
