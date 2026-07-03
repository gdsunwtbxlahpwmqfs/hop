# Pipeline: 0003-pipeline-without-error

## Basic Information

- **Pipeline Name:** 0003-pipeline-without-error
- **Source File:** `03-转换插件/Beam大数据类/samples/0003-pipeline-without-error.hpl`

## Transforms

| Name | Type |
|------|------|
| 10k rows | RowGenerator |
| Nothing | Dummy |
| id | Sequence |

## Hops

| From | To |
|------|----|
| 10k rows | id |
| id | Nothing |
