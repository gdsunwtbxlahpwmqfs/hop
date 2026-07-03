# Pipeline: 0002-pipeline-get-configurations

## Basic Information

- **Pipeline Name:** 0002-pipeline-get-configurations
- **Source File:** `06-元数据类型/samples/0002-pipeline-get-configurations.hpl`

## Transforms

| Name | Type |
|------|------|
| OUTPUT | Dummy |
| files/${TYPE}.properties | PropertyInput |
| get values | Denormaliser |

## Hops

| From | To |
|------|----|
| files/${TYPE}.properties | get values |
| get values | OUTPUT |
