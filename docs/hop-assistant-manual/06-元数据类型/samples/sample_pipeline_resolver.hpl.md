# Pipeline: sample_pipeline_resolver

## Basic Information

- **Pipeline Name:** sample_pipeline_resolver
- **Source File:** `06-元数据类型/samples/sample_pipeline_resolver.hpl`

## Transforms

| Name | Type |
|------|------|
| Calculator | Calculator |
| Get variables | GetVariable |
| OUTPUT | Dummy |

## Hops

| From | To |
|------|----|
| Get variables | Calculator |
| Calculator | OUTPUT |
