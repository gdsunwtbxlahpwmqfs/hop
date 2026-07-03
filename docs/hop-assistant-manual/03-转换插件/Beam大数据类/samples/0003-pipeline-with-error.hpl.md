# Pipeline: 0003-pipeline-with-error

## Basic Information

- **Pipeline Name:** 0003-pipeline-with-error
- **Source File:** `03-转换插件/Beam大数据类/samples/0003-pipeline-with-error.hpl`

## Transforms

| Name | Type |
|------|------|
| 10k rows | RowGenerator |
| id | Sequence |
| over 1000 : throw error | ScriptValueMod |

## Hops

| From | To |
|------|----|
| 10k rows | id |
| id | over 1000 : throw error |
