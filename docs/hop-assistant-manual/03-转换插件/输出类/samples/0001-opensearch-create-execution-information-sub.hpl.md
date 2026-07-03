# Pipeline: 0001-opensearch-create-execution-information-sub

## Basic Information

- **Pipeline Name:** 0001-opensearch-create-execution-information-sub
- **Source File:** `03-转换插件/输出类/samples/0001-opensearch-create-execution-information-sub.hpl`

## Transforms

| Name | Type |
|------|------|
| 500 rows | RowGenerator |
| id | Sequence |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| 500 rows | id |
| id | Output |
