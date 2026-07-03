# Pipeline: sample-pipeline

## Basic Information

- **Pipeline Name:** sample-pipeline
- **Source File:** `03-转换插件/流程控制类/samples/sample-pipeline.hpl`

## Transforms

| Name | Type |
|------|------|
| add-seq | Sequence |
| generate-c-rate | RowGenerator |

## Hops

| From | To |
|------|----|
| generate-c-rate | add-seq |
